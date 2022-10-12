package com.fido.ui.screens.article_details.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.fido.R
import com.fido.databinding.FragmentArticleDetailsBinding

class ArticleDetailsFragment : Fragment() {

    //UI
    private lateinit var binding : FragmentArticleDetailsBinding
    private val navArgs by navArgs<ArticleDetailsFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentArticleDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        initListeners()
    }

    private fun initListeners() {
        binding.fragmentArticleDetailsArrowBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initData() {
        Glide.with(requireContext()).load(navArgs.model.articleImage).placeholder(R.mipmap.ic_launcher).into(binding.articleDetailsImage)
        binding.articleDetailsTitle.text = navArgs.model.articleTitle
        binding.articleDetailsDescription.text = navArgs.model.description
    }
}