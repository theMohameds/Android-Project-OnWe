package com.example.android_project_onwe

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.android_project_onwe.model.Group
import com.example.android_project_onwe.viewmodel.GroupViewModel
import com.example.android_project_onwe.view.screens.GroupDetailsBalanceScreen
import com.example.android_project_onwe.view.screens.GroupDetailsExpenseScreen
import com.example.android_project_onwe.view.screens.GroupDetailsMembersScreen
import com.example.android_project_onwe.view.screens.HomeScreen

@Composable
fun AppNavigation() {
    var currentScreenState by remember { mutableStateOf<Screen>(Screen.Home) }
    val viewModel: GroupViewModel = viewModel()

    when (val screen = currentScreenState) {
        is Screen.Home -> {
            HomeScreen(
                viewModel = viewModel,
                onGroupClick = { group ->
                    currentScreenState = Screen.GroupDetailsExpense(group)
                }
            )
        }
        is Screen.GroupDetailsExpense -> {
            GroupDetailsExpenseScreen(
                group = screen.group,
                viewModel = viewModel,
                onBackClick = { currentScreenState = Screen.Home },
                onBalanceTabClick = {
                    currentScreenState = Screen.GroupDetailsBalance(screen.group)
                },
                onMembersTabClick = {
                    currentScreenState = Screen.GroupDetailsMembers(screen.group)
                }
            )
        }
        is Screen.GroupDetailsBalance -> {
            GroupDetailsBalanceScreen(
                group = screen.group,
                viewModel = viewModel,
                onBackClick = { currentScreenState = Screen.Home },
                onExpensesTabClick = {
                    currentScreenState = Screen.GroupDetailsExpense(screen.group)
                },
                onMembersTabClick = {
                    currentScreenState = Screen.GroupDetailsMembers(screen.group)
                }
            )
        }
        is Screen.GroupDetailsMembers -> {
            GroupDetailsMembersScreen(
                group = screen.group,
                viewModel = viewModel,
                onBackClick = { currentScreenState = Screen.Home },
                onExpensesTabClick = {
                    currentScreenState = Screen.GroupDetailsExpense(screen.group)
                },
                onBalanceTabClick = {
                    currentScreenState = Screen.GroupDetailsBalance(screen.group)
                }
            )
        }
    }
}

sealed class Screen {
    object Home : Screen()
    data class GroupDetailsExpense(val group: Group) : Screen()
    data class GroupDetailsBalance(val group: Group) : Screen()
    data class GroupDetailsMembers(val group: Group) : Screen()
}