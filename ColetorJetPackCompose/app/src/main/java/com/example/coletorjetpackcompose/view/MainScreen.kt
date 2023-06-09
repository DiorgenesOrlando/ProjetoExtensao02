package com.example.coletorjetpackcompose.view

import EntradaDados
import LeitoQr
import LeitoQrDestino
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.coletorjetpackcompose.viewmodel.EntradaDadosViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(  entradaDados : EntradaDadosViewModel = viewModel()
){
    val uiState by entradaDados.entradaDadosUiState.collectAsState()


    val navController = rememberNavController()
    Scaffold(
        topBar = { TopAppBar() {
            Text(text = "Coletor")
        }
        },


    ) {
        NavHost(navController = navController, startDestination = "entrada_dados"){
            composable("entrada_dados"){
                EntradaDados(navController = navController, entradaDados = entradaDados )
            }
            composable("leitor_qr"){
                LeitoQr(navController = navController, entradaDados = entradaDados )
            }
            composable("leitor_qr_destino"){
                LeitoQrDestino(navController = navController, entradaDados = entradaDados )
            }


        }

    }

}