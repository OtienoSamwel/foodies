package com.moose.foodies.features.auth

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.moose.foodies.components.*

@Preview(name = "Light Theme")
@Preview(name = "Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun Signup(){
    val viewmodel: AuthViewmodel = hiltViewModel()
    val loading by viewmodel.loading.observeAsState(false)
    val confirmState = remember { TextFieldState(validators = listOf(Required())) }
    val passwordState = remember { TextFieldState(validators = listOf(Required())) }
    val emailState = remember { TextFieldState(validators = listOf(Email(), Required())) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Don't have an account?")
        Text(text = "Enter your details below to get started")
        SmallSpacing()
        OutlinedInput(
            state = emailState,
            label = "Email address",
            type = KeyboardType.Email,
        )
        OutlinedInput(
            hide = true,
            label = "Password",
            state = passwordState,
            type = KeyboardType.Password,
        )
        OutlinedInput(
            hide = true,
            state = confirmState,
            label = "Confirm password",
            type = KeyboardType.Password,
        )
        SmallSpacing()
        FilledButton(text = "Sign up", size = 0.85f, loading = loading) {
            emailState.validate()
            confirmState.validate()
            passwordState.validate()

            if (confirmState.text != passwordState.text){
                confirmState.showError("passwords do not match")
            } else if (!passwordState.hasError && !emailState.hasError && !loading){
                viewmodel.signup(emailState.text, passwordState.text)
            }
        }
        SmallSpacing()
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
            SmallSpacing()
            TextButton(text = "Back to login", onClick = { viewmodel.changeScreen(0) })
        }
    }
}