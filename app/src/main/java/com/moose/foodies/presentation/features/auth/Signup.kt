package com.moose.foodies.presentation.features.auth

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.moose.foodies.presentation.components.*
import com.moose.foodies.presentation.theme.largeHPadding
import com.moose.foodies.presentation.theme.smallVPadding

@Preview(name = "Light Theme")
@Preview(name = "Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun Signup(){
    val viewmodel: AuthViewmodel = hiltViewModel()
    val loading by viewmodel.loading.observeAsState(false)

    val confirmState = remember { TextFieldState(validators = listOf(Required())) }
    val passwordState = remember { TextFieldState(validators = listOf(Required())) }
    val emailState = remember { TextFieldState(validators = listOf(Email(), Required())) }

    val inputModifier = Modifier.fillMaxWidth().largeHPadding()
    val labelModifier = Modifier.fillMaxWidth().largeHPadding().smallVPadding()

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Don't have an account?")
        Text(text = "Enter your details below to get started")

        SmallSpace()

        Text(text = "Email address", modifier = labelModifier, textAlign = TextAlign.Start)
        TextInput(state = emailState, modifier = inputModifier)

        SmallSpace()

        Text(text = "Password", modifier = labelModifier, textAlign = TextAlign.Start)
        PasswordInput(state = passwordState, modifier = inputModifier)

        SmallSpace()

        Text(text = "Confirm password", modifier = labelModifier, textAlign = TextAlign.Start)
        PasswordInput(state = confirmState, modifier = inputModifier)

        MediumSpace()

        FilledButton(text = "Sign up", modifier = inputModifier, loading = loading) {
            emailState.validate()
            confirmState.validate()
            passwordState.validate()

            if (confirmState.text != passwordState.text){
                confirmState.showError("passwords do not match")
            } else if (!passwordState.hasError && !emailState.hasError && !loading){
                viewmodel.signup(emailState.text, passwordState.text)
            }
        }

        SmallSpace()

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
            SmallSpace()
            TextButton(text = "Back to login", onClick = { viewmodel.changeScreen(0) })
        }
    }
}