import SwiftUI

@main
struct iOSApp: App {
    
    init() {
        KoinKt.doInitKoin()
        FirebaseApp.configure()
        FirebaseApp.crashlytics.setCrashlyticsCollectionEnabled(true)
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
