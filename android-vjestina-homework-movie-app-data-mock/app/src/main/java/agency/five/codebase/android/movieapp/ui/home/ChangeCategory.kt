package agency.five.codebase.android.movieapp.ui.home

fun changeCategory(
    homeMovieCategoryViewState: HomeMovieCategoryViewState,
    id: Int,
): HomeMovieCategoryViewState {
    val movieCategories = homeMovieCategoryViewState.movieCategories.map {
        if (id == it.id) it.copy(isSelected = true)
        else if (it.isSelected) it.copy(isSelected = false)
        else it.copy()
    }
    return homeMovieCategoryViewState.copy(movieCategories = movieCategories)
}
