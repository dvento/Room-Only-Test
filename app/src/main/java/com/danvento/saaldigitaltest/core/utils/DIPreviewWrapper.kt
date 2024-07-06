package com.danvento.saaldigitaltest.core.utils

import androidx.compose.runtime.Composable
import com.danvento.saaldigitaltest.core.provideObjectDao
import com.danvento.saaldigitaltest.core.provideRelationDao
import com.danvento.saaldigitaltest.core.provideRoom
import com.danvento.saaldigitaltest.data.model.DataResult
import com.danvento.saaldigitaltest.data.repository.ObjectRepository
import com.danvento.saaldigitaltest.di.dataModule
import com.danvento.saaldigitaltest.di.roomModule
import com.danvento.saaldigitaltest.di.useCaseModule
import com.danvento.saaldigitaltest.di.viewModelModule
import com.danvento.saaldigitaltest.domain.AddRelationUseCase
import com.danvento.saaldigitaltest.domain.CreateObjectUseCase
import com.danvento.saaldigitaltest.domain.DeleteObjectUseCase
import com.danvento.saaldigitaltest.domain.DeleteRelationUseCase
import com.danvento.saaldigitaltest.domain.GetAllObjectsUseCase
import com.danvento.saaldigitaltest.domain.GetObjectByIdUseCase
import com.danvento.saaldigitaltest.domain.UpdateObjectUseCase
import com.danvento.saaldigitaltest.domain.model.ObjectItem
import com.danvento.saaldigitaltest.ui.viemodel.ObjectDetailViewModel
import com.danvento.saaldigitaltest.ui.viemodel.ObjectListViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.compose.KoinApplication
import org.koin.dsl.module

/*
* Saal Digital Test:
* com.danvento.saaldigitaltest.core.utils
* 
* Created by Dan Vento. 
*/

@Composable
fun DIPreviewWrapper(
    content: @Composable () -> Unit
) {

    KoinApplication(application = {
        modules(
            listOf(
                mockRoomModule,
                mockDataModule,
                mockViewModelModule,
                mockUseCaseModule,
            )
        )
    }) {
        content()
    }
}

val mockRoomModule = module {
    single { provideRoom(get()) }
    single { provideObjectDao(get()) }
    single { provideRelationDao(get()) }
}

val mockDataModule = module {
    single<ObjectRepository> { MockObjectRepository() }
}

val mockViewModelModule = module {
    viewModel { ObjectListViewModel(get(), get()) }
    viewModel { ObjectDetailViewModel(get(), get(), get(), get(), get(), get(), get()) }
}

val mockUseCaseModule = module {
    single<GetAllObjectsUseCase> { GetAllObjectsUseCase(MockObjectRepository()) }
    single<GetObjectByIdUseCase> { GetObjectByIdUseCase(MockObjectRepository()) }
    single<CreateObjectUseCase> { MockCreateObjectUseCase() }
    single<UpdateObjectUseCase> { UpdateObjectUseCase(MockObjectRepository()) }
    single<DeleteObjectUseCase> { MockDeleteObjectUseCase() }
    single<AddRelationUseCase> { AddRelationUseCase(MockObjectRepository()) }
    single<DeleteRelationUseCase> { DeleteRelationUseCase(MockObjectRepository()) }
}

class MockCreateObjectUseCase : CreateObjectUseCase(MockObjectRepository())
class MockDeleteObjectUseCase : DeleteObjectUseCase(MockObjectRepository())
class MockObjectRepository : ObjectRepository {
    // Implement required methods with mock data
    override suspend fun getAllObjects(): Flow<DataResult<List<ObjectItem>>> {
        // Provide mock data here
        return flow {}
    }

    override suspend fun getObjectById(id: Int): DataResult<ObjectItem> {
        return DataResult.Success(ObjectItem(
            id = 1,
            name = "Mock Object",
            type = "Mock Type",
            description = "Mock Description"
        ))
    }

    override suspend fun createObject(objectItem: ObjectItem): DataResult<ObjectItem> {
        // Provide mock data here
        return DataResult.Success(objectItem)
    }

    override suspend fun updateObject(objectItem: ObjectItem): DataResult<ObjectItem> {
        // Provide mock data here
        return DataResult.Success(objectItem)
    }

    override suspend fun deleteObject(objectItem: ObjectItem): DataResult<ObjectItem> {
        // Provide mock data here
        return DataResult.Success(objectItem)
    }

    override suspend fun addRelation(parentId: Int, childId: Int): DataResult<Unit> {
        // Provide mock data here
        return DataResult.Success(Unit)
    }

    override suspend fun deleteRelation(relationId: Int): DataResult<Unit> {
        // Provide mock data here
        return DataResult.Success(Unit)
    }
}

