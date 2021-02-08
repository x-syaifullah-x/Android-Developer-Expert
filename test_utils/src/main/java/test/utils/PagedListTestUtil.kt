package test.utils

import androidx.paging.PagedList
import org.mockito.Mockito

object PagedListTestUtil {
    @Suppress("UNCHECKED_CAST")
    fun <T> mockPagedList(list: List<T>): PagedList<T> {

        val pagedList = Mockito.mock(PagedList::class.java) as PagedList<T>

        Mockito.`when`(pagedList[Mockito.anyInt()]).then { invocation ->
            val index = invocation.arguments.first() as Int
            list[index]
        }
        Mockito.`when`(pagedList.snapshot()).thenReturn(list)
        Mockito.`when`(pagedList.size).thenReturn(list.size)

        return pagedList
    }
}