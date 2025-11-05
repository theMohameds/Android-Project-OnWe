package com.example.android_project_onwe.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_project_onwe.model.Group
import com.example.android_project_onwe.model.Receipt
import com.example.android_project_onwe.model.User
import com.example.android_project_onwe.model.DummyData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class GroupUiState(
    val groups: List<Group> = emptyList(),
    val selectedGroup: Group? = null,
    val receipts: List<Receipt> = emptyList(),
    val users: Map<String, User> = emptyMap(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class GroupViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GroupUiState())
    val uiState: StateFlow<GroupUiState> = _uiState.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                groups = listOf(DummyData.expenseGroup),
                users = DummyData.usersMap,
                receipts = listOf(DummyData.sampleReceipt)
            )
        }
    }

    fun selectGroup(group: Group) {
        _uiState.value = _uiState.value.copy(selectedGroup = group)
    }

    fun createGroup(name: String, memberIds: List<String>) {
        viewModelScope.launch {
            val newGroup = Group(
                name = name,
                members = memberIds,
                totalBalance = 0.0,
                userOwes = 0.0,
                userIsOwed = 0.0
            )
            val updatedGroups = _uiState.value.groups + newGroup
            _uiState.value = _uiState.value.copy(groups = updatedGroups)
        }
    }

    fun getGroupMembers(group: Group): List<User> {
        return group.members.mapNotNull { memberId ->
            _uiState.value.users[memberId]
        }
    }

    fun getReceiptsForGroup(groupId: String): List<Receipt> {
        return _uiState.value.receipts
    }

    fun getUserById(userId: String): User? {
        return _uiState.value.users[userId]
    }
}