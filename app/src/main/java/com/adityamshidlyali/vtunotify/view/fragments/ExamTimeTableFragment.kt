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
import com.adityamshidlyali.vtunotify.databinding.FragmentExamTimetableBinding
import com.adityamshidlyali.vtunotify.model.NotificationItem
import com.adityamshidlyali.vtunotify.repository.NotificationRepository
import com.adityamshidlyali.vtunotify.viewmodels.NotificationViewModel
import com.adityamshidlyali.vtunotify.viewmodels.NotificationViewModelProvider

class ExamTimeTableFragment : Fragment(R.layout.fragment_exam_timetable),
    NotificationItemAdapter.OnItemClickListener {

    private lateinit var viewModel: NotificationViewModel

    private var _binding: FragmentExamTimetableBinding? = null

    private val binding: FragmentExamTimetableBinding
        get() = _binding!!

    var notifications = ArrayList<NotificationItem>()

    lateinit var webView: WebView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExamTimetableBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val notificationRecyclerView: RecyclerView = binding.notificationRecyclerView.recyclerView
        val progressBar: ProgressBar = binding.notificationRecyclerView.progressCircular
        notificationRecyclerView.layoutManager = LinearLayoutManager(context)

        binding.notificationRecyclerView.textViewNotificationTitle.text =
            "Exam Time Table"

        val repository = NotificationRepository(requireContext())
        viewModel = ViewModelProvider(
            this,
            NotificationViewModelProvider(
                requireActivity().applicationContext as Application,
                repository
            )
        )[NotificationViewModel::class.java]

        viewModel.getNotifications("https://vtu.ac.in/en/category/examination/time-table/")

        viewModel.notifications.observe(viewLifecycleOwner, Observer {
            binding.notificationRecyclerView.progressCircular.visibility = View.INVISIBLE
            notifications = it as ArrayList<NotificationItem>
            notificationRecyclerView.adapter = NotificationItemAdapter(notifications, this@ExamTimeTableFragment)
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