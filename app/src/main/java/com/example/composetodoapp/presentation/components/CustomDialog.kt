package com.example.composetodoapp.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.composetodoapp.presentation.navigation.NavigationType

@Composable
fun CustomDialog(
    navController: NavController,
    value: String,
    @StringRes valueRes: Int? = null,
    @StringRes confirmText: Int,
    @StringRes cancelText: Int,
    onConfirmClick: () -> Unit,
) {
    Surface(
        shape = RoundedCornerShape(16.dp), color = Color.White
    ) {
        Box(contentAlignment = Alignment.Center) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.End,
                ) {
                    Icon(imageVector = Icons.Filled.Cancel,
                        contentDescription = "",
                        modifier = Modifier
                            .width(30.dp)
                            .height(30.dp)
                            .clickable { navController.popBackStack() })
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (valueRes != null) {
                            stringResource(id = valueRes, value)
                        } else {
                            value
                        }, style = TextStyle(
                            fontSize = 24.sp,
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Button(
                        onClick = {
                            navController.popBackStack()
                            /**
                             * navController.currentBackStackEntry?.destination?.route
                             * -> 현재 Navigation Route 이름 가져오기
                             */
                            if (navController.currentBackStackEntry?.destination?.route == NavigationType.DETAIL_SCREEN.name) {
                                navController.popBackStack()
                            }
                            onConfirmClick()
                        },
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier
                            .height(50.dp)
                            .padding(end = 10.dp)
                            .weight(1f)
                    ) {
                        Text(text = stringResource(id = confirmText))
                    }
                    Button(
                        onClick = {
                            navController.popBackStack()
                        },
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier
                            .height(50.dp)
                            .padding(start = 10.dp)
                            .weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.White,
                            contentColor = Color.Black,
                        )
                    ) {
                        Text(
                            text = stringResource(id = cancelText), style = TextStyle(color = Color.Black)
                        )
                    }
                }
            }
        }
    }
}