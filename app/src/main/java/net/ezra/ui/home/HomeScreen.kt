package net.ezra.ui.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import net.ezra.R
import net.ezra.navigation.ROUTE_BM
import net.ezra.navigation.ROUTE_HOME
import net.ezra.navigation.ROUTE_HR
import net.ezra.navigation.ROUTE_ME
import net.ezra.navigation.ROUTE_MH
import net.ezra.navigation.ROUTE_OS
import net.ezra.navigation.ROUTE_RB
import net.ezra.navigation.ROUTE_RC
import net.ezra.navigation.ROUTE_REGISTER
import net.ezra.navigation.ROUTE_RO
import net.ezra.navigation.ROUTE_SS
import net.ezra.navigation.ROUTE_ST
import net.ezra.navigation.ROUTE_VIEW_PROD

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreen(navHostController = rememberNavController())
        }
    }
}

@Composable
fun HomeScreen(navHostController: NavHostController) {
    var query by remember { mutableStateOf("") }

    Scaffold(
        bottomBar = { BottomBar(navController = navHostController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            TopBar()
            Spacer(modifier = Modifier.height(16.dp))
            SearchBar(
                query = query,
                onQueryChange = { query = it }
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Hot Movies button
            Button(
                onClick = { /* Handle Hot Movies button click */ },
                shape = RoundedCornerShape(50), // Make the button round
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text("Hot Movies")
            }
            TopMovies(navController = navHostController)
        }
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp)), // Rounded corners
        placeholder = { Text(text = "Search movies...") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon"
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = Color.White,
            focusedBorderColor = MaterialTheme.colors.primary,
            unfocusedBorderColor = Color.Gray,
            focusedLabelColor = MaterialTheme.colors.primary,
            cursorColor = MaterialTheme.colors.primary
        ),
        shape = RoundedCornerShape(8.dp) // Rounded corners for the outline border
    )
}

@Composable
fun TopBar() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Image(
            painter = rememberImagePainter("https://via.placeholder.com/150"), // Replace with your image URL
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "View your favorite movies",
            style = MaterialTheme.typography.body1,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun TopMovies(navController: NavController) {
    val movies = listOf(
        "Sky Scrapper",
        "Marjavaan",
        "Power",
        "Fbi",
        "12 Strong",
        "Mission Impossible"
    )
    val images = listOf(
        R.drawable.movie1, // Replace with your image resources
        R.drawable.movie2,
        R.drawable.movie3,
        R.drawable.movie4,
        R.drawable.movie5,
        R.drawable.movie6
    )

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(movies.size) { index ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(100.dp)
            ) {
                Image(
                    painter = painterResource(id = images[index]),
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = movies[index],
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(15.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = { /*TODO*/ },
            shape = RoundedCornerShape(50) // Make the button round
        ) {
            Text("Hot Movies")
        }
        Button(
            onClick = { /*TODO*/ },
            shape = RoundedCornerShape(50) // Make the button round
        ) {
            Text("Favourites")
        }
    }

    Row {
        Card(
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier.size(170.dp)
                .clickable { navController.navigate(ROUTE_HR) }
            ,
            elevation = 5.dp
        ) {
            Box(
                modifier = Modifier.height(150.dp)
            ) {
                Image(
                    modifier = Modifier.size(170.dp),
                    painter = painterResource(id = R.drawable.black2),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Text(
                        color = Color.White,
                        text = "Black Adam"
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
        Card(
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier.size(170.dp)
                .clickable { navController.navigate(ROUTE_RB) }
            ,
            elevation = 5.dp
        ) {
            Box(
                modifier = Modifier.height(150.dp)
            ) {
                Image(
                    modifier = Modifier.size(170.dp),
                    painter = painterResource(id = R.drawable.atlas),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Text(
                        color = Color.White,
                        text = "Atlas"
                    )
                }
            }
        }
    }

    Row {
        Card(
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier.size(170.dp)
                .clickable { navController.navigate(ROUTE_ST) }
            ,
            elevation = 5.dp
        ) {
            Box(
                modifier = Modifier.height(150.dp)
            ) {
                Image(
                    modifier = Modifier.size(170.dp),
                    painter = painterResource(id = R.drawable.beetle),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Text(
                        color = Color.White,
                        text = "Blue Beetle"
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
        Card(
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier.size(170.dp)
                .clickable { navController.navigate(ROUTE_OS) }
            ,
            elevation = 5.dp
        ) {
            Box(
                modifier = Modifier.height(150.dp)
            ) {
                Image(
                    modifier = Modifier.size(170.dp),
                    painter = painterResource(id = R.drawable.damsel2),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Text(
                        color = Color.White,
                        text = "Damsel"
                    )
                }
            }
        }
    }

    Row {
        Card(
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier.size(170.dp)
                .clickable { navController.navigate(ROUTE_ME) }
            ,
            elevation = 5.dp
        ) {
            Box(
                modifier = Modifier.height(150.dp)
            ) {
                Image(
                    modifier = Modifier.size(170.dp),
                    painter = painterResource(id = R.drawable.wick),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Text(
                        color = Color.White,
                        text = "John wick"
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
        Card(
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier.size(170.dp)
                .clickable { navController.navigate(ROUTE_BM) }
            ,
            elevation = 5.dp
        ) {
            Box(
                modifier = Modifier.height(150.dp)
            ) {
                Image(
                    modifier = Modifier.size(170.dp),
                    painter = painterResource(id = R.drawable.bad2),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Text(
                        color = Color.White,
                        text = "Badland Hunters"
                    )
                }
            }
        }
    }


    Row {
        Card(
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier.size(170.dp)
                .clickable { navController.navigate(ROUTE_RC) }
            ,
            elevation = 5.dp
        ) {
            Box(
                modifier = Modifier.height(150.dp)
            ) {
                Image(
                    modifier = Modifier.size(170.dp),
                    painter = painterResource(id = R.drawable.dark1),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Text(
                        color = Color.White,
                        text = "Dark Asset"
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
        Card(
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier.size(170.dp)
                .clickable { navController.navigate(ROUTE_RO) },




            elevation = 5.dp,

            ) {
            Box(
                modifier = Modifier.height(150.dp)
            ) {
                Image(
                    modifier = Modifier.size(170.dp),
                    painter = painterResource(id = R.drawable.snitch),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Text(
                        color = Color.White,
                        text = "Snitch"
                    )
                }
            }
        }
    }

    Row {
        Card(
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier.size(170.dp)
                .clickable { navController.navigate(ROUTE_MH) }
            ,
            elevation = 5.dp
        ) {
            Box(
                modifier = Modifier.height(150.dp)
            ) {
                Image(
                    modifier = Modifier.size(170.dp),
                    painter = painterResource(id = R.drawable.web2),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Text(
                        color = Color.White,
                        text = "Madame web"
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
        Card(
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier.size(170.dp)
                .clickable { navController.navigate(ROUTE_SS) }
            ,
            elevation = 5.dp
        ) {
            Box(
                modifier = Modifier.height(150.dp)
            ) {
                Image(
                    modifier = Modifier.size(170.dp),
                    painter = painterResource(id = R.drawable.badboys),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Text(
                        color = Color.White,
                        text = "Bad boys for life"
                    )
                }
            }
        }
    }
}

@Composable
fun BottomBar(navController: NavController) {
    val selectedIndex = remember { mutableStateOf(0) }
    BottomNavigation {
        BottomNavigationItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.book), // Replace with your home icon
                    contentDescription = "Home"
                )
            },
            label = { Text("Home") },
            selected = selectedIndex.value == 0,
            onClick = {
                selectedIndex.value = 0
                navController.navigate(ROUTE_HOME)
            }
        )
        BottomNavigationItem(
            icon = {
                Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null)
            },
            label = { Text("Favorites") },
            selected = selectedIndex.value == 1,
            onClick = {
                selectedIndex.value = 1
                navController.navigate(ROUTE_VIEW_PROD) {
                    popUpTo(ROUTE_HOME) { inclusive = true }
                }
            }
        )
        BottomNavigationItem(
            icon = {
                Icon(imageVector = Icons.Default.Person, contentDescription = null)
            },
            label = { Text("Profile") },
            selected = selectedIndex.value == 2,
            onClick = {
                selectedIndex.value = 2
                navController.navigate(ROUTE_REGISTER) {
                    popUpTo(ROUTE_HOME) { inclusive = true }
                }
            }
        )
    }
}
