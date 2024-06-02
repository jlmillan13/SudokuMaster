package com.jlmillan.sudokumaster.ui.feature.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val charactersState by viewModel.stateCharacters.collectAsStateWithLifecycle()
                val loadingState by viewModel.stateLoading.collectAsStateWithLifecycle()
                MainView(
                    loading = loadingState,
                    characterList = charactersState.first ?: emptyList()
                )
                if (charactersState.second != null) {
                    showError()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        with((activity as? MainActivity)) {
            this?.showToolbar(true)
            this?.setToolbarTitle(getString(R.string.characters))
            updateFavoriteIcon()
            this?.showProfile(true) {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToProfileFragment())
            }
        }
    }

    private fun updateFavoriteIcon() {
        (activity as? MainActivity)?.showFavorite(viewModel.isFavoriteChecked) {
            viewModel.isFavoriteChecked = viewModel.isFavoriteChecked.not()
            updateFavoriteIcon()
        }
    }

    private fun showError() {
        showToast(getString(R.string.error_unknown))
    }

    @Composable
    private fun MainView(loading: Boolean, characterList: List<CharacterModel>) {
        val loadingAlpha = if (loading) 0.5f else 0f
        LazyColumn(modifier = Modifier.padding(4.dp)) {
            item {
                Text("This is a header of characters list")
            }
            items(items = characterList) { item ->
                CharacterView(item)
                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                        .background(color = Color.Black)
                )
            }
        }
        LoadingView(alpha = loadingAlpha)
    }
}