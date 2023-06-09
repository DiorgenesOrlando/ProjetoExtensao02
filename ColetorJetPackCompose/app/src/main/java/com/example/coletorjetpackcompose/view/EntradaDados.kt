import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import android.os.Handler
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.example.coletorjetpackcompose.viewmodel.EntradaDadosViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.util.Calendar
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.util.Date
import java.util.TimeZone

@OptIn(ExperimentalUnitApi::class)
@Composable
fun EntradaDados(
    navController: NavController,
    entradaDados: EntradaDadosViewModel
) {
    val uiState by entradaDados.entradaDadosUiState.collectAsState()
    var selectedAbastecedorOption by remember { mutableStateOf("Selecionar Abastecedor") }
    var abastecedorExpanded by remember { mutableStateOf(false) }

    val abastecedorOptions = listOf(1, 2, 3)
    val combustivelOptions = listOf("Gasolina", "Diesel", "GLP")
    var selectedCombustivelOption by remember { mutableStateOf("Selecionar Combustivel") }
    var combustivelExpanded by remember { mutableStateOf(false) }

    val quantidadeState = remember { mutableStateOf("") }
    var isEnviadoVisible by remember { mutableStateOf(false) }
    var verificaEnvio : Boolean = false

    var exibirMensagem by remember {
        mutableStateOf(false)
    }

    var enviado by remember {
        mutableStateOf("")
    }
    var color = Color.Green


    var isVisible by remember { mutableStateOf(true) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            elevation = 4.dp
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = uiState.dados.origem.toString())
                Button(onClick = { navController.navigate("leitor_qr") },   enabled= !exibirMensagem
                ) {
                    Text(text = "Origem")
                }

            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            elevation = 4.dp
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = uiState.dados.destino.toString())
                Button(onClick = { navController.navigate("leitor_qr_destino") }, enabled= !exibirMensagem) {
                    Text(text = "Destino")
                }
            }
        }

        // ESCOLHA DO ABASTECEDOR
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            val abastecedorText = if (uiState.dados.abastecedor != null) {
                uiState.dados.abastecedor.toString()
            } else {
                selectedAbastecedorOption
            }

            Text(
                text = abastecedorText,
                modifier = Modifier.clickable(onClick = { abastecedorExpanded = true })
            )

            DropdownMenu(
                expanded = abastecedorExpanded,
                onDismissRequest = { abastecedorExpanded = false }
            ) {
                abastecedorOptions.forEach { option ->
                    DropdownMenuItem(onClick = {
                        selectedAbastecedorOption = option.toString()
                        uiState.dados.abastecedor = option
                        abastecedorExpanded = false
                    },  enabled= !exibirMensagem)  {
                        Text(text = option.toString())
                    }
                }
            }
        }

        // ESCOLHA DO COMBUSTIVEL
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            val combustivelText = if (uiState.dados.combustivel != null) {
                uiState.dados.combustivelText.toString()
            } else {
                selectedCombustivelOption
            }

            Text(
                text = combustivelText,
                modifier = Modifier.clickable(onClick = { combustivelExpanded = true })
            )

            DropdownMenu(
                expanded = combustivelExpanded,
                onDismissRequest = { combustivelExpanded = false }
            ) {
                combustivelOptions.forEach { option ->
                    DropdownMenuItem(onClick = {
                        selectedCombustivelOption = option.toString()
                        uiState.dados.combustivelText = option.toString()
                        uiState.dados.combustivel = when (option) {
                            "Gasolina" -> 1
                            "Diesel" -> 2
                            else -> 3
                        }
                        combustivelExpanded = false
                    },  enabled= !exibirMensagem) {
                        Text(text = option.toString())
                    }
                }
            }
        }

        // Entrada da quantidade de combustivel
        OutlinedTextField(
            value = quantidadeState.value,

            enabled= !exibirMensagem,
            onValueChange = { quantidadeState.value = it },
            label = { Text("Quantidade") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        if(exibirMensagem == true){
            if(enviado == "ENVIADO"){
                color = Color.Green
            }else if(enviado == "AGUARDE ..."){
                color = Color.DarkGray
            }else{
                color = Color.Red
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = enviado,
                fontSize = TextUnit(20f, TextUnitType.Sp),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(12.dp)
                    .background(color , shape = RoundedCornerShape(4.dp)),
                style = TextStyle(fontWeight = FontWeight.Bold, color = Color.White)
            )
            Button(onClick = {
                exibirMensagem = false

            }, enabled =( enviado != "AGUARDE ...")) {
                Text(text = "OK")
            }
        }


        Spacer(modifier = Modifier.height(16.dp))

        if(!exibirMensagem){

            Button(
                onClick = {
                    exibirMensagem = true
                    enviado = "AGUARDE ..."

                    //uiState.dados.origem = "1"
                    //uiState.dados.destino = "10035511"
                    uiState.dados.quantidade = quantidadeState.value.toIntOrNull()
                    //uiState.dados.combustivel = 2
                    var datHora : String = getHoraAtualUtc()
                    val corpoJson = """
                    {
                        "origem": {
                            "id": ${uiState.dados.origem}
                        },
                        "destino": {
                            "codigoSap": ${uiState.dados.destino}
                        },
                        "abastecedor": {
                            "id": ${uiState.dados.abastecedor}
                        },
                        "quantidade": ${uiState.dados.quantidade},
                        "combustivel": {
                            "id": ${uiState.dados.combustivel}
                        },
                        "dataHoraSaida": "${datHora}"
                        
                    }
                """.trimIndent()
                    //// //"2023-04-19T21:07:56.789-03:00"
                    //                                                    //"2023-05-28T21:18:44.556Z-3:00

                    println(corpoJson)
                    val url = "http://10.153.13.113:8080/saida-combustivel"
                    // uiState.dados.isEnviadoVisible =  true//

                    enviarPost(url, corpoJson){retorno ->

                        verificaEnvio = retorno
                        println("Valor verificaEnvio ${verificaEnvio}")
                        println("Valor DataUtc : ${datHora}")
                        if(verificaEnvio == true){
                            // backColor = Color.Green
                            enviado="ENVIADO"
                        }else{
                            // backColor = Color.Red
                            enviado = "NÃO ENVIADO"
                        }

                    }

                    //navController.navigate("entrada_dados")

                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Salvar")
            }

        }
    }
}




fun enviarPost(url: String, corpo: String, callback: (Boolean) -> Unit) {
    val client = OkHttpClient()

    val mediaType = "application/json".toMediaType()
    val requestBody = corpo.toRequestBody(mediaType)

    val request = Request.Builder()
        .url(url)
        .post(requestBody)
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            // Lidar com o erro de conexão
            println("onFailure: false")
            e.printStackTrace()
            callback(false)
        }
        override fun onResponse(call: Call, response: Response) {
            // Lidar com a resposta do servidor
            if (response.code == 200) {
                val responseBody = response.body?.string()
                println("onResponse: true")

                callback(true)
                println(responseBody)
            } else {
                println("onResponse: false")

                callback(false)

                // Lidar com a resposta não bem sucedida
                //  println("Erro: ${response.code}")
                //println("Retorno: ${retorno}")

            }
        }}
    )
}


fun getHoraAtualUtc(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    dateFormat.timeZone = TimeZone.getTimeZone("UTC")
    val horaAtual = Date()

    val offsetMillis = TimeZone.getDefault().rawOffset
    val offsetHours = offsetMillis / (60 * 60 * 1000)
    val offsetMinutes = Math.abs(offsetMillis / (60 * 1000) % 60)
    val offsetSign = if (offsetMillis >= 0) "+" else ""

    val offset = String.format("%02d:%02d", offsetHours, offsetMinutes)

    return dateFormat.format(horaAtual)
}