package com.example.maprecycling.ui.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun RegisterScreen(navController: NavController) {
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isCreating by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(colors = listOf(Color(0xFFE8F5E9), Color(0xFFC8E6C9)))
            )
    ) {
        Card(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
                .align(Alignment.Center),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF2E7D32)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Person, contentDescription = null, tint = Color.White, modifier = Modifier.size(44.dp))
                }
                Spacer(Modifier.height(12.dp))
                Text("Criar Conta", style = MaterialTheme.typography.headlineMedium, color = Color(0xFF1B5E20))
                Spacer(Modifier.height(18.dp))
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nome") }, leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
                Spacer(Modifier.height(12.dp))
                OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
                Spacer(Modifier.height(12.dp))
                OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Senha") }, leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) }, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
                Spacer(Modifier.height(18.dp))
                Button(onClick = {
                    if (name.isBlank() || email.isBlank() || password.isBlank()) {
                        Toast.makeText(context, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    isCreating = true
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnSuccessListener { result ->
                            val uid = result.user!!.uid
                            val userData = mapOf("name" to name, "email" to email, "createdAt" to System.currentTimeMillis())
                            FirebaseFirestore.getInstance().collection("users").document(uid).set(userData)
                                .addOnSuccessListener {
                                    isCreating = false
                                    Toast.makeText(context, "Conta criada com sucesso", Toast.LENGTH_SHORT).show()
                                    navController.navigate("home") { popUpTo("register") { inclusive = true } }
                                }
                                .addOnFailureListener {
                                    isCreating = false
                                    Toast.makeText(context, it.message ?: "Erro ao salvar usuário", Toast.LENGTH_SHORT).show()
                                }
                        }
                        .addOnFailureListener {
                            isCreating = false
                            Toast.makeText(context, it.message ?: "Erro ao criar conta", Toast.LENGTH_SHORT).show()
                        }
                }, modifier = Modifier.fillMaxWidth().height(52.dp), shape = RoundedCornerShape(14.dp), enabled = !isCreating, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))) {
                    if (isCreating) CircularProgressIndicator(color = Color.White, modifier = Modifier.size(22.dp)) else Text("Cadastrar", color = Color.White)
                }
                Spacer(Modifier.height(12.dp))
                TextButton(onClick = { navController.navigate("login") }) { Text("Já tem conta? Entrar") }
            }
        }
    }
}
