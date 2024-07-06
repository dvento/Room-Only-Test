package com.danvento.saaldigitaltest.di

import com.danvento.saaldigitaltest.core.provideObjectDao
import com.danvento.saaldigitaltest.core.provideRelationDao
import com.danvento.saaldigitaltest.core.provideRoom
import com.danvento.saaldigitaltest.data.model.DataResult
import com.danvento.saaldigitaltest.data.repository.ObjectRepository
import com.danvento.saaldigitaltest.data.repository.ObjectRepositoryImpl
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
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/*
* Saal Digital Test:
* com.danvento.saaldigitaltest.di
* 
* Created by Dan Vento. 
*/

val roomModule = module {
    single { provideRoom(get()) }
    single { provideObjectDao(get()) }
    single { provideRelationDao(get()) }
}

val dataModule = module {
    single<ObjectRepository> { ObjectRepositoryImpl(get(), get()) }
}

val viewModelModule = module {
    viewModel { ObjectListViewModel(get(), get()) }
    viewModel { ObjectDetailViewModel(get(), get(), get(), get(), get(), get(), get()) }
}

val useCaseModule = module {
    single { GetAllObjectsUseCase(get()) }
    single { GetObjectByIdUseCase(get()) }
    single { CreateObjectUseCase(get()) }
    single { UpdateObjectUseCase(get()) }
    single { DeleteObjectUseCase(get()) }
    single { AddRelationUseCase(get()) }
    single { DeleteRelationUseCase(get()) }
}

