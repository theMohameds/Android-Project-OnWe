package com.example.android_project_onwe.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.android_project_onwe.model.Group
import com.example.android_project_onwe.viewmodel.GroupViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupDetailsBalanceScreen(
    group: Group,
    viewModel: GroupViewModel = viewModel(),
    onBackClick: () -> Unit = {},
    onExpensesTabClick: () -> Unit = {},
    onMembersTabClick: () -> Unit = {},
    onChatClick: () -> Unit = {}
) {
    val members = viewModel.getGroupMembers(group)

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text(group.name) },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(Icons.Default.ArrowBack, "Back")
                        }
                    },
                    actions = {
                        IconButton(onClick = { }) {
                            Icon(Icons.Default.MoreVert, "More")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFFF5F5F5)
                    )
                )

                TabRow(
                    selectedTabIndex = 1,
                    containerColor = Color(0xFFF5F5F5),
                    contentColor = Color.Black
                ) {
                    Tab(
                        selected = false,
                        onClick = onExpensesTabClick,
                        text = { Text("Expenses", fontSize = 12.sp) }
                    )
                    Tab(
                        selected = true,
                        onClick = { },
                        text = { Text("Balance", fontSize = 12.sp) }
                    )
                    Tab(
                        selected = false,
                        onClick = onMembersTabClick,
                        text = { Text("Members", fontSize = 12.sp) }
                    )
                    Tab(
                        selected = false,
                        onClick = onChatClick,
                        text = { Text("Chat now", fontSize = 12.sp) }
                    )
                }
            }
        },
        bottomBar = {
            BottomNavigationBar(selectedItem = "home")
        },
        containerColor = Color(0xFFF5F5F5)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            // Summary card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "You paid in total:",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Text(
                            "5989.00 kr",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Total payments:",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Text(
                            "20535.00 kr",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Each pay:",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Text(
                            "5133.75 kr",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Divider()
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "You owes/owed",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Red
                        )
                    }
                }
            }

            Text(
                "Members who didn't pay",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Members list
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(members.filter { it.isOwed }) { member ->
                    MemberBalanceItem(
                        memberName = member.name,
                        amount = member.owedAmount,
                        isOwe = true
                    )
                }
            }
        }
    }
}

@Composable
fun MemberBalanceItem(
    memberName: String,
    amount: Double,
    isOwe: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE0E0E0)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        memberName.first().toString(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        memberName,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        if (isOwe) "Alice [OWES]" else "Paid",
                        fontSize = 12.sp,
                        color = if (isOwe) Color.Red else Color.Green
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { }) {
                    Icon(
                        Icons.Default.MailOutline,
                        contentDescription = "Message",
                        tint = Color.Gray
                    )
                }
                IconButton(onClick = { }) {
                    Icon(
                        Icons.Default.Notifications,
                        contentDescription = "Notify",
                        tint = Color.Gray
                    )
                }
            }
        }
    }
}