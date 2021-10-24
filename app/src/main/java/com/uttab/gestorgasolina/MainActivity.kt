package com.uttab.gestorgasolina

import android.annotation.SuppressLint
import android.graphics.Paint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uttab.gestorgasolina.ui.theme.GestorGasolinaTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            app()
        }
    }
}

@SuppressLint("UnusedTransitionTargetStateParameter")
@Preview(showSystemUi = true)
@Composable
fun app() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        //variables mutables para hacer las operaciones segun la opcion seleccionada
        var text1 by remember { mutableStateOf("") }
        var label1 by remember { mutableStateOf("Litros Consumidos") }
        var label2 by remember { mutableStateOf("Kilometros Recorridos") }
        var text2 by remember { mutableStateOf("") }
        var resultado by remember { mutableStateOf("Resultado") }

        //lista mutable para el spinner
        val sampleList =
            mutableListOf("Calcular consumo por cada 100 KM", "Consumo por KM recorridos")
        var sampleName: String by remember { mutableStateOf(sampleList[0]) }
        var expanded by remember { mutableStateOf(false) }
        val transitionState =
            remember { MutableTransitionState(expanded).apply { targetState = !expanded } }
        val transition = updateTransition(targetState = transitionState, label = "transition")
        val arrowRotationDegree by transition.animateFloat({
            tween(durationMillis = 300)
        }, label = "rotationDegree") {
            if (expanded) 180f else 0f
        }
        val context = LocalContext.current
//imagen
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null
        )
        //fila donde se colocara el spinner que cambiara el texto al hacer click
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 13.dp)
            .clickable {
                expanded = !expanded
            }) {
            Text(
                text = sampleName,
                fontSize = 16.sp
            )
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "Spinner",
                modifier = Modifier.rotate(arrowRotationDegree)
            )
            DropdownMenu(expanded = expanded,
                onDismissRequest = { expanded = false })
            {
                sampleList.forEach { data ->
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            sampleName = data
                        })
                    {
                        Text(text = data)

                    }
                }
            }

        }
        //Texfield donde se colocara el primer valor requerido
        TextField(
            value = text1,
            onValueChange = { text1 = it },
            label = { Text(label1) },
            maxLines = 2,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .padding(top = 13.dp)
                .fillMaxWidth()
        )
//texfield para el valor numero 2
        TextField(
            value = text2,
            onValueChange = { text2 = it },
            label = { Text(label2) },
            maxLines = 2,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .padding(top = 13.dp)
                .fillMaxWidth()
        )
        /*if donde se cambiara el valor de los labels de los texfield cada que cambie
        el valor del spinner*/
        if (sampleName == "Calcular consumo por cada 100 KM") {
            label1 = "Litros Consumidos"
            label2 = "Kilometros Recoridos"
        } else if (sampleName == "Consumo por KM recorridos") {
            label1 = "Kilometros a recorrer/recorridos"
            label2 = "Consumo de L por cada 100 KM"
        }
        //variable donde se realizaran las operaciones
        var rs: Int = 0

        //fila donde se encuentra el buton y el resultado
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 13.dp)
        ) {
            Button(
                //se realiza if para realizar una operacion segun el elemento seleccionado
                onClick = {
                    if (sampleName == "Calcular consumo por cada 100 KM") {
                        rs = (text1.toInt() * 100) / text2.toInt()
                        resultado=rs.toString()
                    } else if (sampleName == "Consumo por KM recorridos") {
                        rs =(text1.toInt() * text2.toInt())/100
                    resultado=rs.toString()
                    }

                }
            ) {
                Text(text = "Calcular")

            }
            Text(
                text = resultado,
                fontSize = 18.sp,
                color = Color.Green,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}