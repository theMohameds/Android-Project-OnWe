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
import com.example.android_project_onwe.model.User
import com.example.android_project_onwe.viewmodel.GroupViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupDetailsMembersScreen(
    group: Group,
    viewModel: GroupViewModel = viewModel(),
    onBackClick: () -> Unit = {},
    onExpensesTabClick: () -> Unit = {},
    onBalanceTabClick: () -> Unit = {},
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
                    selectedTabIndex = 2,
                    containerColor = Color(0xFFF5F5F5),
                    contentColor = Color.Black
                ) {
                    Tab(
                        selected = false,
                        onClick = onExpensesTabClick,
                        text = { Text("Expenses", fontSize = 12.sp) }
                    )
                    Tab(
                        selected = false,
                        onClick = onBalanceTabClick,
                        text = { Text("Balance", fontSize = 12.sp) }
                    )
                    Tab(
                        selected = true,
                        onClick = { },
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(members) { member ->
                MemberCard(member = member)
            }
        }
    }
}

@Composable
fun MemberCard(member: User) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                // Member avatar
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE0E0E0)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        member.name.first().toString(),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        member.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    if (member.isOwed) {
                        Text(
                            "You are owed",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                        Text(
                            "Haven't paid yet",
                            fontSize = 12.sp,
                            color = Color.Red
                        )
                    }
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { }) {
                    Icon(
                        Icons.Default.Call,
                        contentDescription = "Message",
                        tint = Color.Gray
                    )
                }
                IconButton(onClick = { }) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "Profile",
                        tint = Color.Gray
                    )
                }
            }
        }
    }
}

// Alternative card with image
@Composable
fun MemberCardWithImage(
    member: User,
    imageRes: Int? = null
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE0E0E0)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            member.name.first().toString(),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            member.name,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            "2520",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Text(
                            "You are owed",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                        Text(
                            "Haven't paid yet",
                            fontSize = 12.sp,
                            color = Color.Red
                        )
                    }
                }

                Row {
                    IconButton(onClick = { }) {
                        Icon(
                            Icons.Default.Call,
                            contentDescription = "Message",
                            tint = Color.Gray
                        )
                    }
                    IconButton(onClick = { }) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = "Profile",
                            tint = Color.Gray
                        )
                    }
                }
            }

            // Optional large image (like Superman image in Figma)
            if (imageRes != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFE0E0E0)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = Color.Gray
                    )
                }
                Text(
                    "1032 - Jeff",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}