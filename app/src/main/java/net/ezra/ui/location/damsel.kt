package net.ezra.ui.location

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import net.ezra.R
import net.ezra.navigation.ROUTE_ADD_PRODUCT

class Branch4Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            osscreen(navHostController = rememberNavController())
        }
    }
}

@Composable
fun osscreen(navHostController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1E1E2F))
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.damsel),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(RoundedCornerShape(20.dp))
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Damsel",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFFD700),
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Text(
            text = "Rating: 6.8/10",
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFFE91E63),
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Genre: Fantasy, Adventure",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF00BFFF),
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Director: Juan Carlos Fresnadillo",
            fontSize = 18.sp,
            color = Color(0xFFB0C4DE),
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Text(
            text = "Cast: Millie Bobby Brown, Angela Bassett, Robin Wright",
            fontSize = 18.sp,
            color = Color(0xFFB0C4DE),
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Ticket Price: \$13.99",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF00BFFF),
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Synopsis",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF00BFFF),
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "A young woman is framed for the murder of a brutal ruler and must navigate a dangerous world to prove her innocence and discover the true enemy.",
            fontSize = 16.sp,
            color = Color(0xFFB0C4DE),
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { navHostController.navigate(ROUTE_ADD_PRODUCT) },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 8.dp)
                .clip(RoundedCornerShape(20.dp)),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4CAF50))
        ) {
            Text(text = "Book Now", fontSize = 18.sp, color = Color.White)
        }
    }
}


