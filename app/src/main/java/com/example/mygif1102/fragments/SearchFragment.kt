package com.example.mygif1102.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_search.view.*
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import com.example.mygif1102.Constants
import com.example.mygif1102.OnFragmentInteractionListener
import com.example.mygif1102.R
import com.example.mygif1102.SearchAdapter
import com.example.mygif1102.http.Giphy
import com.example.mygif1102.model.GifImage
import com.example.mygif1102.model.GifMessage
import com.example.mygif1102.networks.GifsResponse
import com.example.mygif1102.networks.IGiphyService
import kotlinx.android.synthetic.main.fragment_detail.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [SearchFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class SearchFragment : Fragment(), View.OnClickListener{

    private var listener: OnFragmentInteractionListener? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_search, container, false)
        view.imageSearch.setOnClickListener { showGifs(view) }
        view.imageButtonBackSearch.setOnClickListener(this)
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
        when(v?.id){
            R.id.imageButtonBackSearch -> activity?.onBackPressed()
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun showGifs(view: View) {
        val q = editSearch.text.toString()
        /*IGiphyService.instance.getSearches(getString(R.string.giphy_api_key), q, 200, 0)
            .enqueue(object : Callback<GifsResponse> {
                override fun onResponse(call: Call<GifsResponse>?, response: Response<GifsResponse>?) {
                    val dataList = response!!.body()!!.datas
                    val items = ArrayList<GifImage>()
                    dataList.forEach {
                        items.add(
                            GifImage(
                                it.id,
                                it.images.fixedWidthJson.width.toInt(),
                                it.images.fixedWidthJson.height.toInt(),
                                it.images.fixedWidthJson.url
                            )
                        )
                    }
                    Log.d("items", items.toString())
                    view.recyclerSearches.layoutManager =
                        StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                    view.recyclerSearches.adapter = SearchAdapter(items, onItemClick = {
                        listener?.onFragmentInteraction(
                            GifMessage(
                                Constants.MESSAGE_TYPE_GIF,
                                it!!.id
                            )
                        )
//                        activity!!.supportFragmentManager.beginTransaction()
//                            .add(R.id.constraintMain, DetailFragment())
//                            .addToBackStack(null)
//                            .commit()
                    })
                }

                override fun onFailure(call: Call<GifsResponse>?, t: Throwable?) {
                    Log.e("Error", t.toString())
                }
            })
*/
        Giphy.instance.getSearches(getString(R.string.giphy_api_key), q,20, 0, onGetResult = { gifImages ->
            view.recyclerSearches.apply {
                layoutManager = StaggeredGridLayoutManager(2, 1)
                adapter = SearchAdapter(gifImages, onItemClick = { gifImage ->
                    listener?.onFragmentInteraction(
                        GifMessage(
                            Constants.MESSAGE_TYPE_GIF,
                            gifImage!!.id
                        )
                    )
                })
            }

        })

    }



}

