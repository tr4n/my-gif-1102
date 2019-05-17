package com.example.mygif1102.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_search.view.*
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Config
import android.util.Log
import com.example.mygif1102.BuildConfig
import com.example.mygif1102.utils.MESSAGE_TYPE_GIF
import com.example.mygif1102.OnFragmentInteractionListener
import com.example.mygif1102.R
import com.example.mygif1102.SearchAdapter
import com.example.mygif1102.http.Giphy
import com.example.mygif1102.http.OnResponseListener
import com.example.mygif1102.model.GifImage
import com.example.mygif1102.model.GifMessage
import com.example.mygif1102.model.GifsResponse
import com.example.mygif1102.recyclerview.EndlessRecyclerViewScrollListener
import com.example.mygif1102.recyclerview.SpeedyStaggeredGridLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_search.*
import java.lang.Exception


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val TAG = "SearchFragment"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [SearchFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class SearchFragment : Fragment(), View.OnClickListener {
    private var gifOffset = 0
    private val staggeredGridLayoutManager = SpeedyStaggeredGridLayoutManager(2, 1)
    private val gifImages = ArrayList<GifImage>()
    private var gifAdapter: SearchAdapter? = null
    var q = ""
    private var listener: OnFragmentInteractionListener? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        view.imageButtonBackSearch.setOnClickListener(this)
        gifAdapter = SearchAdapter(gifImages, onItemClick = { gifImage ->
            listener?.onFragmentInteraction(
                GifMessage(
                    MESSAGE_TYPE_GIF,
                    gifImage!!.id
                )
            )
        })
        view.recyclerSearches.apply {
            layoutManager = staggeredGridLayoutManager
            adapter = gifAdapter
        }
        view.searchProgressBar.isIndeterminate = true

        view.imageSearch.setOnClickListener { showGifs() }

        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {

        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imageButtonBackSearch -> activity?.onBackPressed()
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun showGifs() {
        gifImages.clear()
        gifOffset = 0
        gifAdapter?.updateData(ArrayList<GifImage>())
        q = editSearch.text.toString()

        Giphy.instance.getSearches(
            BuildConfig.GIPHY_API_KEY,
            q,
            20,
            0,
            object : OnResponseListener<GifsResponse> {
                override fun onSuccess(response: GifsResponse) {
                    gifAdapter?.insertData(response.gifImages)
                    gifOffset += response.gifImages.size
                    setLoadMoreItems()
                }

                override fun onFailed(exception: Exception) {
                    Log.d(TAG, "#onFailed: ${exception.message}")
                }
            })

    }

    private fun setLoadMoreItems() {
        view?.apply {

            searchProgressBar.visibility = View.GONE
            recyclerSearches.addOnScrollListener(object :
                EndlessRecyclerViewScrollListener(staggeredGridLayoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int) {

                    searchProgressBar.visibility = View.VISIBLE
                    Giphy.instance.getSearches(
                        BuildConfig.GIPHY_API_KEY,
                        q,
                        30,
                        gifOffset,
                        object : OnResponseListener<GifsResponse> {
                            override fun onSuccess(response: GifsResponse) {
                                gifAdapter?.insertData(response.gifImages)
                                gifOffset += response.gifImages.size
                                Log.d(TAG, "gifOffset = $gifOffset")

                                searchProgressBar.visibility = View.GONE

                                gifAdapter?.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
                                    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                                        // super.onItemRangeInserted(positionStart, itemCount);
                                        staggeredGridLayoutManager.smoothScrollToPosition(
                                            recyclerSearches,
                                            null,
                                            positionStart + 5
                                        )

                                    }
                                })
                            }

                            override fun onFailed(exception: Exception) {
                                Log.e(TAG, "onFailed: ${exception.message}")
                            }
                        })
                }

            })
        }
    }


}

