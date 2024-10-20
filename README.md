
<img src="https://github.com/user-attachments/assets/c5f156bc-d8d2-46f6-8007-d185bc2b966c" alt="photo" width="150" height="350"/>
<img src="https://github.com/user-attachments/assets/f198dac7-9903-4453-88e1-64f8ab94eeee" alt="photo" width="150" height="350"/>
<img src="https://github.com/user-attachments/assets/b78764d1-5938-4937-88b5-766cafbc700a" alt="photo" width="150" height="350"/>
<img src="https://github.com/user-attachments/assets/cee847ca-5056-4ac9-ab2f-cf6c84bd621f" alt="photo" width="150" height="350"/>
<img src="https://github.com/user-attachments/assets/65b9f9cd-483c-4352-ad77-b3f08e72468a" alt="photo" width="150" height="350"/>
<img src="https://github.com/user-attachments/assets/df3ca4c1-b3ce-472e-ab3e-2fd8346daade" alt="photo" width="150" height="350"/>
<img src="https://github.com/user-attachments/assets/a0ea0e74-64b4-4b5a-80a4-24383774f77e" alt="photo" width="150" height="350"/>
<img src="https://github.com/user-attachments/assets/6128e184-6b71-4ba1-90c4-cfbf853deb2e" alt="photo" width="150" height="350"/>
<img src="https://github.com/user-attachments/assets/a70b4451-198f-41ef-81d0-969c62a2b1bd" alt="photo" width="150" height="350"/>
<img src="https://github.com/user-attachments/assets/dd118666-b781-4b7a-9b55-8708867b37f1" alt="photo" width="150" height="350"/>
## Dependencies

This project uses the following dependencies:

### Networking
- **Retrofit**: `implementation ("com.squareup.retrofit2:retrofit:2.9.0")`
- **Gson Converter**: `implementation ("com.squareup.retrofit2:converter-gson:2.9.0")`
- **Volley**: `implementation("androidx.volley:volley:1.2.1")`

### Room Database
- **Room Runtime**: `implementation("androidx.room:room-runtime:2.5.0")`
- **Room Compiler**: `kapt("androidx.room:room-compiler:2.5.0")`
- **Room KTX** (optional): `implementation("androidx.room:room-ktx:2.5.0")`

### Google Maps
- **Maps Compose**: `implementation("com.google.maps.android:maps-compose:4.3.3")`

### Dependency Injection
- **Hilt**: `implementation("com.google.dagger:hilt-android:2.48.1")`
- **Hilt Compiler**: `kapt("com.google.dagger:hilt-android-compiler:2.48.1")`
- **Hilt Navigation**: `implementation("androidx.hilt:hilt-navigation-compose:1.2.0")`

### Navigation
- **Navigation Compose**: `implementation("androidx.navigation:navigation-compose:2.6.0")`

### Google Services
- **Location Services**: `implementation("com.google.android.gms:play-services-location:21.1.0")`

### UI Libraries
- **Compose Material Icons**: `implementation("androidx.compose.material:material-icons-extended:1.5.0")`
- **Accompanist Permissions**: `implementation("com.google.accompanist:accompanist-permissions:0.35.0-alpha")`
- **Compose Google Fonts**: `implementation("androidx.compose.ui:ui-text-google-fonts:1.7.3")`

### Additional Libraries
- **Searchable Dropdown Menu**: `implementation("com.github.Breens-Mbaka:Searchable-Dropdown-Menu-Jetpack-Compose:0.2.8")`

### Core and Lifecycle
- **AndroidX Core KTX**: `implementation(libs.androidx.core.ktx)`
- **AndroidX Lifecycle Runtime KTX**: `implementation(libs.androidx.lifecycle.runtime.ktx)`
- **Activity Compose**: `implementation(libs.androidx.activity.compose)`

### Compose BOM
- **Compose BOM**: `implementation(platform(libs.androidx.compose.bom))`

### Additional UI Components
- **Compose UI**: `implementation(libs.androidx.ui)`
- **Compose Graphics**: `implementation(libs.androidx.ui.graphics)`
- **Compose Tooling Preview**: `implementation(libs.androidx.ui.tooling.preview)`
- **Material3**: `implementation(libs.androidx.material3)`

## Features

### User-Friendly Station Selection
- **Select Any Station**: Users can easily select from a list of available metro stations.

### Nearest Station Locator
- **Current Location**: The app shows the nearest station based on the user's current location.
- **Destination-Based Stations**: Displays the closest stations along the route to the selected destination.

### Route Management
- **Save Routes**: Users can save their frequently used routes.
- **Route Details**: Detailed view of saved routes, including time, pricing, and additional information.

### Multilingual Support
- **Language Options**: The app supports both Arabic and English languages for a wider user base.

### Pricing Information
- **Metro Ticket Costs**: Provides users with information on metro ticket pricing.
- **Cost Calculation**: Automatically calculates the cost of the metro fare based on the selected route.

### Metro Operation Schedule
- **Schedule Access**: Users can view the operational schedule for all metro lines.

### Map Integration
- **Map Display**: Visual representation of the nearest station according to the user's location on the map.



