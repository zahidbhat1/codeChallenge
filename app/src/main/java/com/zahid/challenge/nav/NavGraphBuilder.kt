package com.zahid.challenge.nav

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.zahid.challenge.ui.AllEmployeeScreen
import com.zahid.challenge.ui.CreateEmployeeScreen
import com.zahid.challenge.viewModels.EmployeeViewModel


fun NavGraphBuilder.createEmployeeScreen(viewModel: EmployeeViewModel, navController: NavController) {
    composable(
        "createEmployeeScreen/{empId}",
        arguments = listOf(navArgument("empId") { type = NavType.StringType })
    ) {
        val employeeId = it.arguments?.getString("empId")
        // Argument is passed, you can use the employeeId
        CreateEmployeeScreen(viewModel, navController, employeeId)
    }
    composable(
        "createEmployeeScreen",
    ) {
        // Argument is not passed, handle accordingly (e.g., show an error message)
        CreateEmployeeScreen(viewModel, navController)
    }
}

fun NavGraphBuilder.allEmployeeScreen(
    viewModel: EmployeeViewModel,
    navController: NavHostController,
) {
    composable(
        "AllEmployeeScreen"
    ) {
        AllEmployeeScreen(viewModel, navController)
    }
}
