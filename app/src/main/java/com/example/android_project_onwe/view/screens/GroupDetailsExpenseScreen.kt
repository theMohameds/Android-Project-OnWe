package com.example.android_project_onwe.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.android_project_onwe.model.Receipt
import com.example.android_project_onwe.viewmodel.GroupViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupDetailsExpenseScreen(
    group: Group,
    viewModel: GroupViewModel = viewModel(),
    onBackClick: () -> Unit = {},
    onBalanceTabClick: () -> Unit = {},
    onMembersTabClick: () -> Unit = {},
    onChatClick: () -> Unit = {}
) {
    val receipts = viewModel.getReceiptsForGroup(group.id)

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

                // Tab row
                TabRow(
                    selectedTabIndex = 0,
                    containerColor = Color(0xFFF5F5F5),
                    contentColor = Color.Black
                ) {
                    Tab(
                        selected = true,
                        onClick = { },
                        text = { Text("Expenses", fontSize = 12.sp) }
                    )
                    Tab(
                        selected = false,
                        onClick = onBalanceTabClick,
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(receipts) { receipt ->
                ExpenseCard(receipt = receipt, viewModel = viewModel)
            }
        }
    }
}

@Composable
fun ExpenseCard(
    receipt: Receipt,
    viewModel: GroupViewModel
) {
    val paidByUser = viewModel.getUserById(receipt.paidById)
    val dateFormatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
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
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Expense title:",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Text(
                    text = receipt.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text("Paid by:", fontSize = 12.sp, color = Color.Gray)
                Text(
                    paidByUser?.name ?: "Unknown",
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(4.dp))
                Text("Paid for:", fontSize = 12.sp, color = Color.Gray)
                Text(
                    receipt.splitMemberIds.size.toString() + " members",
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(4.dp))
                Text("Amount:", fontSize = 12.sp, color = Color.Gray)
                Text(
                    "${receipt.amount} kr.",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))
                Text("Split:", fontSize = 12.sp, color = Color.Gray)
                Text(
                    "${receipt.splitAmountPerPerson} kr. each",
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(4.dp))
                Text("Date:", fontSize = 12.sp, color = Color.Gray)
                Text(dateFormatter.format(receipt.date))

                Spacer(modifier = Modifier.height(4.dp))
                Text("Place:", fontSize = 12.sp, color = Color.Gray)
                Text(receipt.place, fontSize = 14.sp)

                Spacer(modifier = Modifier.height(8.dp))
                Text("Receipt:", fontSize = 12.sp, color = Color.Gray)
            }

            // Receipt image placeholder
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFE0E0E0)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.AccountBox,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}