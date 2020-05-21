//
//  ViewController.swift
//  Content Header
//
//  Created by Rob Whitaker on 26/12/2019.
//  Copyright © 2019 RWAPP. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    @IBOutlet weak var pageTitle: UILabel?
    @IBOutlet weak var subtitle: UILabel?
    @IBOutlet weak var likeButton: UIButton?
    @IBOutlet weak var content: UILabel?

    private var liked = false

    override func viewDidLoad() {
        super.viewDidLoad()

        updateHeart()
    }

    private func updateHeart() {
        likeButton?.setImage(heartImage(), for: .normal)
    }

    private func heartImage() -> UIImage {
        return liked ? UIImage(named: "heart-filled")! : UIImage(named: "heart")!
    }

    @IBAction
    func pressedLike(_ sender: Any) {
        liked.toggle()
        updateHeart()
    }
}

