package com.example.mygif1102.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mygif1102.*
import com.example.mygif1102.http.Giphy
import com.example.mygif1102.model.GifImage
import com.example.mygif1102.model.GifMessage
import com.example.mygif1102.model.TitleMessage
import com.example.mygif1102.networks.IGiphyService
import com.example.mygif1102.networks.GifsResponse
import kotlinx.android.synthetic.main.fragment_home.*
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
 * [HomeFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
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
                Constants.MESSAGE_TYPE_TITLE,
                getString(R.string.home),
                R.drawable.ic_home_24dp
            )
        )
//        IGiphyService.instance.getTrendings(getString(R.string.giphy_api_key), 20, 0)
//            .enqueue(object : Callback<GifsResponse> {
//                override fun onResponse(call: Call<GifsResponse>?, response: Response<GifsResponse>?) {
//                    val dataList = response!!.body()!!.datas
//                    val items = ArrayList<GifImage>()
//                    dataList.forEach {
//                        items.add(
//                            GifImage(
//                                it.id,
//                                it.images.fixedWidthJson.width.toInt(),
//                                it.images.fixedWidthJson.height.toInt(),
//                                it.images.fixedWidthJson.url
//                            )
//                        )
//                    }
//                    Log.d("items", items.toString())
//                    recyclerTrendingGifs.layoutManager = StaggeredGridLayoutManager(2, 1)
//                    recyclerTrendingGifs.adapter = SearchAdapter(items, onItemClick = {
//                        listener?.onFragmentInteraction(
//                            GifMessage(
//                                Constants.MESSAGE_TYPE_GIF,
//                                it!!.id
//                            )
//                        )
////                        activity!!.supportFragmentManager.beginTransaction()
////                            .addToBackStack(null)
////                            .replace(R.id.constraintMain, DetailFragment()).commit()
//                    })
//                }
//
//                override fun onFailure(call: Call<GifsResponse>?, t: Throwable?) {
//                    Log.e("Error", t.toString())
//                }
//            })

        Giphy.instance.getTrendings(getString(R.string.giphy_api_key), 20, 0, onGetResult = { gifImages ->
            recyclerTrendingGifs.apply {
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


