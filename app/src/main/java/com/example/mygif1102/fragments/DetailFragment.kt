package com.example.mygif1102.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mygif1102.BuildConfig
import com.example.mygif1102.utils.EXTRA_GIF
import com.example.mygif1102.OnFragmentInteractionListener
import com.example.mygif1102.R
import com.example.mygif1102.SearchAdapter
import com.example.mygif1102.utils.SCREEN_WIDTH
import com.example.mygif1102.gifview.GIFView
import com.example.mygif1102.http.Giphy
import com.example.mygif1102.http.OnResponseListener
import com.example.mygif1102.model.GifMessage
import com.example.mygif1102.model.GifResponse
import com.example.mygif1102.model.GifsResponse
import com.example.mygif1102.utils.MESSAGE_TYPE_GIF
import kotlinx.android.synthetic.main.fragment_detail.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.lang.Exception


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val TAG = "DetailFragment"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [DetailFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class DetailFragment : Fragment() {


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
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        val gifMessage = arguments?.getParcelable(EXTRA_GIF) as? GifMessage
        // if (gifMessage != null)
        /* IGiphyService.instance.getGif(gifMessage.id, getString(R.string.giphy_api_key))
             .enqueue(object : Callback<GifResponse> {
                 override fun onFailure(call: Call<GifResponse>, t: Throwable) {

                 }

                 override fun onResponse(call: Call<GifResponse>, response: Response<GifResponse>) {
                     val data = response!!.body()!!.data
                     Log.d("TAG123", data.images.original.url)
                     view.textTitleDetail.text = data.title
                     view.textSourceDetail.text = if (data.sourceTld.isNotEmpty()) data.sourceTld else "giphy.com"

//                        Glide.with(view.context)
//                            .asGif()
//                            .load(data.images.original.gifStream)
//                            .placeholder(Constants.COLORS[Random.nextInt(Constants.COLORS.size)])
//                            .into(view.imageDetail)
//                            .clearOnDetach()

                     val url = data.images.original.url
                     val width = data.images.original.width.toInt()
                     val height = data.images.original.height.toInt()
                     val fixedWidth = (Resources.getSystem().displayMetrics.widthPixels)
                     val fixedHeight = (height * fixedWidth) / width
                     val layoutParams = LinearLayout.LayoutParams(fixedWidth, fixedHeight)
                     view.detailGIFView.setLayoutParams(width, height, fixedWidth, GIFView.WIDTH_SCALE)
                     view.detailGIFView.gifStream = url

                 }

             })
     listener?.onFragmentInteraction(
         TitleMessage(
             Constants.MESSAGE_TYPE_TITLE,
             getString(R.string.detail),
             R.drawable.ic_search_24dp
         )
     )*/
        /* Giphy.instance.getGif(gifMessage.id, getString(R.string.giphy_api_key), onGetResult = { gifImage ->

             view.detailGIFView.apply {
                 setLayoutParams(gifImage.width, gifImage.height, SCREEN_WIDTH - 10, GIFView.WIDTH_SCALE)
                 gifStream = gifImage.url
             }
         })*/
        gifMessage?.let {
            Giphy.instance.getGif(it.id, BuildConfig.GIPHY_API_KEY, object : OnResponseListener<GifResponse> {
                override fun onSuccess(response: GifResponse) {
                    view.detailGIFView.apply {

                        setLayoutParams(
                            response.gifImage.width,
                            response.gifImage.height,
                            SCREEN_WIDTH - 10,
                            GIFView.WIDTH_SCALE
                        )

                        gifStream = response.gifImage.url
                    }
                    view.textTitleDetail.text = response.title
                    view.textSourceDetail.text = if (response.source.isEmpty()) "giphy.com" else response.source
                }

                override fun onFailed(exception: Exception) {
                    Log.e(TAG, "onFailed: ${exception.message}")
                }
            })
        }

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
         * @return A new instance of fragment DetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
