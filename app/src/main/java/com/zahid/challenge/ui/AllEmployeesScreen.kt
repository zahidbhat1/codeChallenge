package com.zahid.challenge.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.zahid.challenge.R
import com.zahid.challenge.networkUtils.models.EmployeeModel
import com.zahid.challenge.observeInComposition
import com.zahid.challenge.viewModels.EmployeeViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllEmployeeScreen(viewModel: EmployeeViewModel, navController: NavHostController) {

    var employeeData by remember { mutableStateOf<List<EmployeeModel>>(emptyList()) }
    var allEmployeesData by remember { mutableStateOf<List<EmployeeModel>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var search by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    LaunchedEffect(Unit) {
        viewModel.loadEmployees()
    }
    viewModel.employeesList.observeInComposition { fetchedEmployees ->
        // Update the local state with the fetched employees
        allEmployeesData = fetchedEmployees
        employeeData = allEmployeesData
        error = ""
    }
    viewModel.error.observeInComposition {
        error = it
        isLoading = false
        employeeData = emptyList()
    }
    viewModel.loading.observeInComposition {
        isLoading = it
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "All Employees") },
                actions = {
                    Button(onClick = { viewModel.loadEmployees() }) {
                        Text(text = "Refresh")
                    }
                    Button(onClick = { navController.navigate("createEmployeeScreen") }) {
                        Text(text = "Add")
                    }
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(66.dp))
                OutlinedTextField(
                    value = search,
                    onValueChange = {
                        search = it
                        employeeData = allEmployeesData.filter { employeeModel ->
                            employeeModel.employee_name.lowercase()
                                .contains(search.lowercase())
                        }
                    },
                    label = { Text("Search By Name") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
                if (isLoading)
                    CircularProgressIndicator()
                error?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
                EmployeeList(
                    employees = employeeData,
                    onItemClick = { navController.navigate("createEmployeeScreen/${it.id}") })
            }
        }

    )

}

@Composable
fun EmployeeList(
    employees: List<EmployeeModel>,
    onItemClick: (EmployeeModel) -> Unit
) {
    LazyColumn {
        items(employees.size) { i ->
            EmployeeItem(employee = employees[i], onItemClick = onItemClick)
        }
    }
}

@Composable
fun EmployeeItem(employee: EmployeeModel, onItemClick: (EmployeeModel) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onItemClick(employee) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Employee Picture
            Image(
                painter = painterResource(id = R.drawable.ic_android_black_24dp),
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.background)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Employee Details
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = employee.employee_name
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "$${employee.employee_salary}"
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Age: ${employee.employee_age}"
                )
            }
        }
    }
}