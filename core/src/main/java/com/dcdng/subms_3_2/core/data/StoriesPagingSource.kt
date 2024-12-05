package com.dcdng.subms_3_2.core.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dcdng.subms_3_2.core.data.source.remote.network.ApiService
import com.dcdng.subms_3_2.core.data.source.remote.response.StoryListResponse

class StoriesPagingSource(private val apiService: ApiService) : PagingSource<Int, StoryListResponse>() {

  private companion object {
    const val INITIAL_PAGE_INDEX = 1
  }

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StoryListResponse> {
    return try {
      val page = params.key ?: INITIAL_PAGE_INDEX
      val responseData = apiService.listAllStories(page, params.loadSize)
      val dataList = responseData.listStory

      LoadResult.Page(
        data = dataList,
        prevKey = if (page == INITIAL_PAGE_INDEX) null else page - 1,
        nextKey = if (dataList.isNullOrEmpty()) null else page + 1
      )
    } catch (exception: Exception) {
      return LoadResult.Error(exception)
    }
  }

  override fun getRefreshKey(state: PagingState<Int, StoryListResponse>): Int? {
    return state.anchorPosition?.let { anchorPosition ->
      val anchorPage = state.closestPageToPosition(anchorPosition)
      anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
    }
  }
}