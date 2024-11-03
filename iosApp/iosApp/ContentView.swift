import UIKit
import SwiftUI
import ComposeApp
import Lottie

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.mainViewController(
            lottieUIViewController: { animationName in
                return UIHostingController(rootView: DmsLottieView(animationName: animationName))
            }
        )
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ComposeView()
                .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
    }
}
