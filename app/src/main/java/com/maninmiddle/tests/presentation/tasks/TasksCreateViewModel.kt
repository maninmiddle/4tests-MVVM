package com.maninmiddle.tests.presentation.tasks

import androidx.lifecycle.ViewModel
import com.maninmiddle.tests.data.repository.TestsRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TasksCreateViewModel @Inject constructor(
    private val repositoryImpl: TestsRepositoryImpl
) : ViewModel() {
}