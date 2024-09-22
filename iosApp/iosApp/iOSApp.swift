import SwiftUI
import ComposeApp
import FirebaseCore

@main
struct iOSApp: App {
    
    init() {
        KoinKt.doInitKoin()
        FirebaseApp.configure()
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
