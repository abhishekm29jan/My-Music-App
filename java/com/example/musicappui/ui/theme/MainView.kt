package com.example.musicappui.ui.theme

// Required imports
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.musicappui.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView() {
    val scaffoldState = rememberScaffoldState()     // To control the drawer
    val scope = rememberCoroutineScope()            // Coroutine scope to launch open/close drawer
    val viewModel : MainViewModel = viewModel()     // View model to handle navigation of screens

    // Allows us to find out in which route we currently are
    val controller = rememberNavController()
    val navBackStackEntry by controller.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val currentScreen = remember {viewModel.currentScreen.value}

    //this is used to open the dialog
    val dialogOpen = remember { mutableStateOf(false) }

    // Change that current screen to title
    val title = remember { mutableStateOf(currentScreen.title) }

    val isSheetFullScreen by remember { mutableStateOf(false) }
    val modifier = if(isSheetFullScreen) Modifier.fillMaxSize() else Modifier.fillMaxWidth()

    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = {it != ModalBottomSheetValue.HalfExpanded}
    )
    val roundedCornerRadius = if (isSheetFullScreen) 0.dp else 12.dp

    // Bottom navigation bar which is used to navigate between screens
    val BottomBar : @Composable () -> Unit = {
        if (currentScreen is Screen.DrawerScreen || currentScreen == Screen.BottomScreen.Home){

            BottomNavigation(modifier = Modifier.wrapContentSize()) {
                screensInBottom.forEach {
                    item ->
                    val isselected = currentRoute == item.bRoute
                    Log.d(
                        "Navigation",
                        "Item: ${item.btitle}, Current Route: $currentRoute, Is Selected: $isselected"
                    )

                    val tint = if (isselected) Color.White else Color.Black
                    BottomNavigationItem(
                        selected =  currentRoute == item.bRoute,
                        onClick = {
                            controller.navigate(item.bRoute)
                            title.value = item.btitle
                                  },
                        icon = {
                            Icon(
                                tint = tint,
                                painter = painterResource(id = item.icon),
                                contentDescription = item.btitle
                            )
                        },
                        label = {Text(text = item.btitle, color = tint)},
                        selectedContentColor = Color.White,
                        unselectedContentColor = Color.Black
                    )
                }
            }
        }
    }

    ModalBottomSheetLayout(
        sheetState =  modalSheetState,
        sheetShape = RoundedCornerShape(topStart = roundedCornerRadius, topEnd = roundedCornerRadius),
        sheetContent = { MoreBottomSheet(modifier = modifier) }
    )
    {
        Scaffold(
            bottomBar = {BottomBar()},
            topBar = {
                TopAppBar(
                    title = { Text(text = title.value) }, // Shows screen title (Account/Subscription/Add Account)
                    actions = {
                        IconButton(
                            onClick = {
                                // Open the bottom sheet when icon is clicked
                                scope.launch {
                                    if (modalSheetState.isVisible)
                                        modalSheetState.hide()
                                    else
                                        modalSheetState.show()
                                }
                            }
                        ){
                            Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
                        }
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                // Open the drawer when icon is clicked
                                scope.launch { scaffoldState.drawerState.open() }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = "Menu"
                            )
                        }
                    }
                )
            },
            scaffoldState = scaffoldState, // Connect scaffold with drawer
            drawerContent = {
                // Drawer content - shows list of items
                LazyColumn(modifier = Modifier.padding(16.dp)) {
                    items(screensInDrawer) { item ->
                        DrawerItem(
                            selected = currentRoute == item.dRoute,
                            item = item,
                            onDrawerItemClicked = {
                                // Close drawer on click
                                scope.launch { scaffoldState.drawerState.close() }

                                // If special route (like add_account), open dialog or something else
                                if (item.dRoute == "add_account") {
                                    // open the dialog (not implemented here)
                                    dialogOpen.value = true
                                } else {
                                    // Navigate to the selected screen and update the title
                                    controller.navigate(item.dRoute)
                                    title.value = item.dtitle
                                }
                            }
                        )
                    }
                }
            }
        ) {
            // Main content of the screen
            Navigation(navController = controller, viewModel = viewModel, pd = it)

            // This is used to open the dialog
            AccountDialog(dialogOpen = dialogOpen)
        }
    }


    // Scaffold is the base layout structure for topBar, drawer, and content
}

@Composable
fun DrawerItem(
    selected: Boolean, // Highlight if current item is selected
    item: Screen.DrawerScreen, // One drawer screen item
    onDrawerItemClicked: () -> Unit // What happens when clicked
) {
    val background = if (selected) Color.DarkGray else Color.Transparent

    // Each drawer item is a Row with icon and text
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 16.dp)
            .background(background) // Highlight if selected
            .clickable { onDrawerItemClicked() } // Action when user taps
    ) {
        Icon(
            painter = painterResource(id = item.icon), // Set icon
            contentDescription = item.dtitle,
            modifier = Modifier.padding(start = 16.dp)
        )
        Text(
            text = item.dtitle, // Set text
            modifier = Modifier.padding(start = 16.dp),
            style = MaterialTheme.typography.h6 // Use a clear title style
        )
    }
}

@Composable
fun MoreBottomSheet(modifier : Modifier) {
    Box(){
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ){
            Row(modifier = Modifier.padding(16.dp)) {
                Icon(
                    modifier = Modifier.padding(end= 8.dp),
                    painter =  painterResource(id = R.drawable.baseline_settings_24),
                    contentDescription = "Settings"
                )
                Text(text = "Settings", fontSize = 20.sp, color = Color.Black)
            }

            Row(modifier = Modifier.padding(16.dp)) {
                Icon(
                    modifier = Modifier.padding(end = 8.dp),
                    painter = painterResource(id = R.drawable.baseline_share_24),
                    contentDescription = "Share"
                )
                Text(text = "Share", fontSize = 20.sp, color = Color.Black)
            }

            Row(modifier = Modifier.padding(16.dp)) {
                Icon(
                    modifier = Modifier.padding(end = 8.dp),
                    painter = painterResource(id = R.drawable.baseline_help_24),
                    contentDescription = "Help"
                )
                Text(text = "Help", fontSize = 20.sp, color = Color.Black)
            }
        }
    }
}

@Composable
fun Navigation(navController: NavController, viewModel: MainViewModel, pd: PaddingValues) {
    NavHost(navController = navController as NavHostController,
        startDestination = Screen.DrawerScreen.Account.route,
        Modifier.padding(pd)
    ) {
        // These navigation composable are for bottom navigation bar
        composable(Screen.BottomScreen.Home.route) {
            Home()
        }

        composable(Screen.BottomScreen.library.route) {
            Library()
        }

        composable(Screen.BottomScreen.Browse.route) {
            Browse()
        }

        // These navigation composable are for drawer navigation bar
        composable(Screen.DrawerScreen.Account.route){
            AccountView()
        }

        composable(Screen.DrawerScreen.Subscription.route){
            Subscription()
        }
// Note : we are not using composable for add_account since our app will not navigate to its screen but instead of that we want it as a popup

    }
}