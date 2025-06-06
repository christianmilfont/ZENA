import { createNativeStackNavigator } from "@react-navigation/native-stack";
import RotasScreen from "../screens/RotasScreen";
import DashboardScreen from "../screens/DashBoardScreen";
import PerfilScreen from "../screens/PerfilScreen";
import LoginScreen from "../screens/LoginScreen";
import { NavigationContainer } from "@react-navigation/native";
import HomeScreen from "../screens/HomeScreen";
import EditarPerfilScreen from "../screens/EditarPerfilScreen";

const Stack = createNativeStackNavigator();
export default function AppNavigation() {
  return (
    <NavigationContainer>
    <Stack.Navigator initialRouteName="Home" screenOptions={{headerShown:false}}>
    <Stack.Screen name="Home" component={HomeScreen} />
    <Stack.Screen name="Rotas" component={RotasScreen} />
    <Stack.Screen name="DashBoard" component={DashboardScreen} />
    <Stack.Screen name="Login" component={LoginScreen} />
    <Stack.Screen name="Perfil" component={PerfilScreen} />
     <Stack.Screen name="EditarPerfil" component={EditarPerfilScreen} />
   </Stack.Navigator>
   </NavigationContainer>
  );
     
}