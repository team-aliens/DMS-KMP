import UIKit
import SwiftUI
import Lottie

struct DmsLottieView: UIViewRepresentable {
    var animationName: String
    
    func makeUIView(context: Context) -> LottieAnimationView {
        let animationView = LottieAnimationView(name: animationName)

        animationView.translatesAutoresizingMaskIntoConstraints = false
        
    
        animationView.contentMode = .scaleAspectFit
        
        animationView.backgroundColor = UIColor.clear
        animationView.isOpaque = false
        
        animationView.play()
            
        
        
        return animationView
    }
    
    func updateUIView(_ uiView: LottieAnimationView, context: Context) {
        uiView.backgroundColor = UIColor.clear
        
        NSLayoutConstraint.activate([
            uiView.widthAnchor.constraint(equalTo: uiView.widthAnchor),
            uiView.heightAnchor.constraint(equalTo: uiView.heightAnchor)
        ])
    }
}
