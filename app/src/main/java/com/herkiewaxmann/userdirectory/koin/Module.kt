package com.herkiewaxmann.userdirectory.koin

import com.herkiewaxmann.userdirectory.data.UsersRepo
import com.herkiewaxmann.userdirectory.data.UsersRepoImpl
import com.herkiewaxmann.userdirectory.ui.UserDetailViewModel
import com.herkiewaxmann.userdirectory.ui.UserListViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::UserListViewModel)
    viewModelOf(::UserDetailViewModel)

    singleOf(::UsersRepoImpl) bind UsersRepo::class
}


