import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieapps.data.moviedbapi.MovieDbService
import com.example.movieapps.data.moviedbapi.response.MovieItem
import com.example.movieapps.data.moviedbapi.response.ReviewItem
import retrofit2.HttpException
import java.io.IOException

class ReviewPagingSource(
    private val apiService:MovieDbService,
    private val movieId: Int
) : PagingSource<Int, ReviewItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ReviewItem> {
        val nextPageNumber = params.key ?: 1
        try {
            val response = apiService.getMovieReviews(
                movieId = movieId,
                page = nextPageNumber
            )
            val reviewItems = response.body()?.results?.filterNotNull() ?: emptyList()
            return LoadResult.Page(
                data = reviewItems,
                prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1,
                nextKey = if (response.body()?.results.isNullOrEmpty()) null else nextPageNumber + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ReviewItem>): Int? {
        return null
    }
}