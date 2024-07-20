package com.adityamshidlyali.vtunotify.view.fragments

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adityamshidlyali.vtunotify.R
import com.adityamshidlyali.vtunotify.adapter.NotificationItemAdapter
import com.adityamshidlyali.vtunotify.databinding.FragmentExamCircularBinding
import com.adityamshidlyali.vtunotify.model.NotificationItem
import com.adityamshidlyali.vtunotify.repository.NotificationRepository
import com.adityamshidlyali.vtunotify.viewmodels.NotificationViewModel
import com.adityamshidlyali.vtunotify.viewmodels.NotificationViewModelProvider

class ExamCircularsFragment : Fragment(R.layout.fragment_exam_circular),
    NotificationItemAdapter.OnItemClickListener {

    private lateinit var viewModel: NotificationViewModel

    private var _binding: FragmentExamCircularBinding? = null

    private val binding: FragmentExamCircularBinding
        get() = _binding!!

    var notifications = ArrayList<NotificationItem>()

    lateinit var webView: WebView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExamCircularBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val notificationRecyclerView: RecyclerView = binding.notificationRecyclerView.recyclerView
        val progressBar: ProgressBar = binding.notificationRecyclerView.progressCircular
        notificationRecyclerView.layoutManager = LinearLayoutManager(context)

        binding.notificationRecyclerView.textViewNotificationTitle.text =
            "Exam Circulars and Notifications"

        val repository = NotificationRepository(requireContext())
        viewModel = ViewModelProvider(
            this,
            NotificationViewModelProvider(
                requireActivity().applicationContext as Application,
                repository
            )
        )[NotificationViewModel::class.java]

        viewModel.getNotifications("https://vtu.ac.in/category/examination/")

        viewModel.notifications.observe(viewLifecycleOwner, Observer {
            binding.notificationRecyclerView.progressCircular.visibility = View.INVISIBLE
            notifications = it as ArrayList<NotificationItem>
            notificationRecyclerView.adapter = NotificationItemAdapter(notifications, this)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(position: Int) {
        val linkFromCallBack = notifications[position].link
        val uri: Uri = Uri.parse(linkFromCallBack)
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }
}