package net.ezra.ui.Movies

import android.annotation.SuppressLint
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import net.ezra.navigation.ROUTE_HOME
import net.ezra.navigation.ROUTE_VIEW_PROD
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(navController: NavController, onProductAdded: () -> Unit) {
    var movieName by remember { mutableStateOf("") }
    var movieDescription by remember { mutableStateOf("") }
    var ticketPrice by remember { mutableStateOf("") }
    var movieImageUri by remember { mutableStateOf<Uri?>(null) }

    var movieNameError by remember { mutableStateOf(false) }
    var movieDescriptionError by remember { mutableStateOf(false) }
    var ticketPriceError by remember { mutableStateOf(false) }
    var movieImageError by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            movieImageUri = it
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Add Movie", fontSize = 30.sp, color = Color.White)
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(ROUTE_VIEW_PROD)
                    }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            "backIcon",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF2196F3), // Exotic purple color
                    titleContentColor = Color.White,
                )
            )
        },
        content = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF31312F)) // Light golden background
                    .padding(16.dp)
            ) {
                item {
                    if (movieImageUri != null) {
                        Image(
                            painter = rememberImagePainter(movieImageUri), // Using rememberImagePainter with Uri
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No Image Selected", modifier = Modifier.padding(8.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { launcher.launch("image/*") }) {
                        Text(
                            "Select Image",
                            color = Color.Gray
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = movieName,
                        onValueChange = { movieName = it },
                        label = { Text("Movie Name", color = Color.White) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White.copy(alpha = 0.1f)),
                        isError = movieNameError,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.White,
                            textColor = Color.White
                        )
                    )
                    if (movieNameError) {
                        Text("Movie Name is required", color = Color.Red)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = movieDescription,
                        onValueChange = { movieDescription = it },
                        label = { Text("Movie Description", color = Color.White) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White.copy(alpha = 0.1f)),
                        isError = movieDescriptionError,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.White,
                            textColor = Color.White
                        )
                    )
                    if (movieDescriptionError) {
                        Text("Movie Description is required", color = Color.Red)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = ticketPrice,
                        onValueChange = { ticketPrice = it },
                        label = { Text("Ticket Price", color = Color.White) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        keyboardActions = KeyboardActions(onDone = { /* Handle Done action */ }),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White.copy(alpha = 0.1f)),
                        isError = ticketPriceError,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.White,
                            textColor = Color.White
                        )
                    )
                    if (ticketPriceError) {
                        Text("Ticket Price is required", color = Color.Red)
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    if (movieImageError) {
                        Text("Movie Image is required", color = Color.Red)
                    }

                    Button(
                        onClick = {
                            movieNameError = movieName.isBlank()
                            movieDescriptionError = movieDescription.isBlank()
                            ticketPriceError = ticketPrice.isBlank()
                            movieImageError = movieImageUri == null

                            if (!movieNameError && !movieDescriptionError && !ticketPriceError && !movieImageError) {
                                addHotelToFirestore(
                                    navController,
                                    onProductAdded,
                                    movieName,
                                    movieDescription,
                                    ticketPrice.toDouble(),
                                    movieImageUri
                                )
                            }
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF3B5998)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .padding(15.dp)
                    ) {
                        Text("Add Movie", color = Color.White)
                    }
                }
            }
        }
    )
}

private fun addHotelToFirestore(navController: NavController, onProductAdded: () -> Unit, movieName: String, movieDescription: String, ticketPrice: Double, movieImageUri: Uri?) {
    if (movieName.isEmpty() || movieDescription.isEmpty() || ticketPrice.isNaN() || movieImageUri == null) {
        return
    }

    val movieId = UUID.randomUUID().toString()

    val firestore = Firebase.firestore
    val hotelData = hashMapOf(
        "name" to movieName,
        "description" to movieDescription,
        "price" to ticketPrice,
        "imageUrl" to ""
    )

    firestore.collection("movies").document(movieId)
        .set(hotelData)
        .addOnSuccessListener {
            uploadImageToStorage(movieId, movieImageUri) { imageUrl ->
                firestore.collection("movies").document(movieId)
                    .update("imageUrl", imageUrl)
                    .addOnSuccessListener {
                        Toast.makeText(
                            navController.context,
                            "Movie added successfully!",
                            Toast.LENGTH_SHORT
                        ).show()

                        navController.navigate(ROUTE_HOME)

                        onProductAdded()
                    }
                    .addOnFailureListener { e ->
                        // Handle error updating hotel document
                    }
            }
        }
        .addOnFailureListener { e ->
            // Handle error adding hotel to Firestore
        }
}

private fun uploadImageToStorage(hotelId: String, imageUri: Uri?, onSuccess: (String) -> Unit) {
    if (imageUri == null) {
        onSuccess("")
        return
    }

    val storageRef = Firebase.storage.reference
    val imagesRef = storageRef.child("hotels/$hotelId.jpg")

    imagesRef.putFile(imageUri)
        .addOnSuccessListener { taskSnapshot ->
            imagesRef.downloadUrl
                .addOnSuccessListener { uri ->
                    onSuccess(uri.toString())
                }
                .addOnFailureListener {
                    // Handle failure to get download URL
                }
        }
        .addOnFailureListener {
            // Handle failure to upload image
        }
}