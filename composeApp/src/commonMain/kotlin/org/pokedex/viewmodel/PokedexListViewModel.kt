package org.pokedex.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.pokedex.data.local.entity.PokemonCacheEntity
import org.pokedex.data.model.PokemonType
import org.pokedex.data.repository.PokemonRepository

data class PokedexListState(
    val isInitialLoading: Boolean = true,
    val isLoadingMore: Boolean = false,
    val items: List<PokemonCacheEntity> = emptyList(),
    val error: String? = null,
    val hasMore: Boolean = true
)

class PokedexListViewModel(
    private val repository: PokemonRepository
) : ViewModel() {

    private val _state = MutableStateFlow(PokedexListState())
    val state: StateFlow<PokedexListState> = _state.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedType = MutableStateFlow<PokemonType?>(null)
    val selectedType: StateFlow<PokemonType?> = _selectedType.asStateFlow()

    private val pageSize = 20
    private var currentOffset = 0
    private var isLoadingPage = false

    init {
        initialize()
    }

    private fun initialize() {
        viewModelScope.launch {
            _state.update { it.copy(isInitialLoading = true, error = null) }
            try {
                repository.syncIfNeeded()
                resetAndLoad()
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isInitialLoading = false,
                        error = "Erro ao sincronizar dados: ${e.message}"
                    )
                }
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        resetAndLoad()
    }

    fun onTypeSelected(type: PokemonType?) {
        _selectedType.value = type
        resetAndLoad()
    }

    fun loadNextPage() {
        if (isLoadingPage || !_state.value.hasMore || _state.value.isInitialLoading) return
        viewModelScope.launch {
            isLoadingPage = true
            _state.update { it.copy(isLoadingMore = true) }
            try {
                val page = repository.getPage(
                    query = _searchQuery.value,
                    type = _selectedType.value?.name?.lowercase(),
                    limit = pageSize,
                    offset = currentOffset
                )
                currentOffset += page.size
                _state.update { current ->
                    current.copy(
                        isLoadingMore = false,
                        items = current.items + page,
                        hasMore = page.size == pageSize
                    )
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoadingMore = false, error = "Erro ao carregar: ${e.message}") }
            } finally {
                isLoadingPage = false
            }
        }
    }

    private fun resetAndLoad() {
        currentOffset = 0
        isLoadingPage = false
        viewModelScope.launch {
            _state.update { it.copy(isInitialLoading = true, items = emptyList(), hasMore = true, error = null) }
            try {
                val page = repository.getPage(
                    query = _searchQuery.value,
                    type = _selectedType.value?.name?.lowercase(),
                    limit = pageSize,
                    offset = 0
                )
                currentOffset = page.size
                _state.update {
                    it.copy(
                        isInitialLoading = false,
                        items = page,
                        hasMore = page.size == pageSize
                    )
                }
            } catch (e: Exception) {
                _state.update { it.copy(isInitialLoading = false, error = "Erro ao carregar: ${e.message}") }
            }
        }
    }
}
