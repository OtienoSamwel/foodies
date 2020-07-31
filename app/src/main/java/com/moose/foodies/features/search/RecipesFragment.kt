package com.moose.foodies.features.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.moose.foodies.R
import com.moose.foodies.util.*
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.error_404.*
import kotlinx.android.synthetic.main.fragment_recipes.*
import javax.inject.Inject


class RecipesFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var searchViewModel: SearchViewModel

    override fun onAttach(context: Context) {
        this.requireActivity().hideBottomBar()
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        searchViewModel = ViewModelProviders.of(requireActivity(), viewModelFactory).get(SearchViewModel::class.java)
        return inflater.inflate(R.layout.fragment_recipes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchViewModel.exception.observe(viewLifecycleOwner, Observer {
            searchViewModel.loadState.value = false
            showSnackbar(fragment_recipes, it)
        })

        searchViewModel.recipes.observe(viewLifecycleOwner, Observer {
            searchViewModel.loadState.value = false
            recipes_loading.hide()
            if (it.isEmpty()) {
                not_found.text = this.resources.getString(R.string.not_found, "recipes")
                error_layout.show()
            }
            recipes_recycler.apply {
                setHasFixedSize(true)
                adapter = RecipeResultsAdapter(it)
            }
        })
    }

}