package com.maninmiddle.tests.presentation.confirm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maninmiddle.tests.data.model.TaskModel
import com.maninmiddle.tests.data.model.TestModel
import com.maninmiddle.tests.data.model.VariantModel
import com.maninmiddle.tests.data.repository.TestsRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestConfirmViewModel @Inject constructor(
    private val repositoryImpl: TestsRepositoryImpl
) : ViewModel() {


}