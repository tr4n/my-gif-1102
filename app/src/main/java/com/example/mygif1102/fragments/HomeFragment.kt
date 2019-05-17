package com.example.mygif1102.fragments

import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mygif1102.*
import com.example.mygif1102.http.Giphy
import com.example.mygif1102.http.OnResponseListener
import com.example.mygif1102.model.GifMessage
import com.example.mygif1102.model.GifsResponse
import com.example.mygif1102.model.TitleMessage
import com.example.mygif1102.recyclerview.SpeedyStaggeredGridLayoutManager
import com.example.mygif1102.utils.MESSAGE_TYPE_GIF
import com.example.mygif1102.utils.MESSAGE_TYPE_TITLE
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.lang.Exception
import android.support.v7.widget.RecyclerView
import android.os.CountDownTimer
import com.example.mygif1102.model.GifImage
import com.example.mygif1102.recyclerview.EndlessRecyclerViewScrollListener


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val TAG = "HomeFragment"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [HomeFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */


class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var gifOffset = 0
    private val staggeredGridLayoutManager = SpeedyStaggeredGridLayoutManager(2, 1)
    private val gifImages = ArrayList<GifImage>()
    private var gifAdapter: SearchAdapter? = null

    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        listener?.onFragmentInteraction(
            TitleMessage(
                MESSAGE_TYPE_TITLE,
                getString(R.string.home),
                R.drawable.ic_home_24dp
            )
        )
        gifAdapter = SearchAdapter(gifImages, onItemClick = { gifImage ->
            listener?.onFragmentInteraction(
                GifMessage(
                    MESSAGE_TYPE_GIF,
                    gifImage!!.id
                )
            )
        })
        view.recyclerTrendingGifs.apply {
            layoutManager = staggeredGridLayoutManager
            adapter = gifAdapter
        }

        view.progressBar.isIndeterminate = true

      //  setLoadMoreItems()

        Giphy.instance.getTrendingGifs(BuildConfig.GIPHY_API_KEY, 20, 0, object : OnResponseListener<GifsResponse> {
            override fun onSuccess(response: GifsResponse) {
                gifAdapter?.insertData(response.gifImages)
                gifOffset += response.gifImages.size
                setLoadMoreItems()
            }

            override fun onFailed(exception: Exception) {
                Log.e(TAG, "onFailed: ${exception.message}")
            }
        })

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun setLoadMoreItems() {
        view?.apply {

            progressBar.visibility = View.GONE
            recyclerTrendingGifs.addOnScrollListener(object :
                EndlessRecyclerViewScrollListener(staggeredGridLayoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int) {

                    progressBar.visibility = View.VISIBLE

                    Giphy.instance.getTrendingGifs(
                        BuildConfig.GIPHY_API_KEY,
                        30,
                        gifOffset,
                        object : OnResponseListener<GifsResponse> {
                            override fun onSuccess(response: GifsResponse) {
                                gifAdapter?.insertData(response.gifImages)
                                gifOffset += response.gifImages.size
                                Log.d(TAG, "gifOffset = $gifOffset")
                                progressBar.visibility = View.GONE
                                gifAdapter?.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
                                    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                                        // super.onItemRangeInserted(positionStart, itemCount);
                                        staggeredGridLayoutManager.smoothScrollToPosition(
                                            recyclerTrendingGifs,
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


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}


