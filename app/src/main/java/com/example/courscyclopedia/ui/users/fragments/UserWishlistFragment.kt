//import android.os.Bundle
//import android.view.View
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.ViewModelProvider
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.courscyclopedia.databinding.FragmentUserWishlistBinding
////import com.example.courscyclopedia.ui.users.adapter.WishlistAdapter
//import com.example.courscyclopedia.ui.users.viewmodels.WishlistViewMozdel
//import com.example.courscyclopedia.network.RetrofitClient
//import com.example.courscyclopedia.repository.UserRepository
//import com.example.courscyclopedia.ui.users.viewmodels.WishlistViewModelFactory
//import com.google.firebase.auth.FirebaseAuth
//
//class UserWishlistFragment : Fragment(R.layout.fragment_user_wishlist) {
//    private var _binding: FragmentUserWishlistBinding? = null
//    private val binding get() = _binding!!
//    private lateinit var viewModel: WishlistViewModel
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        _binding = FragmentUserWishlistBinding.bind(view)
//
//        setupViewModel()
//        setupRecyclerView()
//    }
//
//    private fun setupViewModel() {
//        val apiService = RetrofitClient.apiService
//        val userRepository = UserRepository(apiService)
//        val factory = WishlistViewModelFactory(subjectsRepository) // Assuming you have a ViewModelFactory
//        viewModel = ViewModelProvider(this, factory).get(WishlistViewModel::class.java)
//
//        val userEmail = FirebaseAuth.getInstance().currentUser?.email ?: return
//        viewModel.fetchWishlistSubjects(userEmail)
//    }
//
//    private fun setupRecyclerView() {
//        val wishlistAdapter = WishlistAdapter { subject ->
//            // Handle subject item click here, for example, navigate to subject detail fragment
//        }
//
//        binding.recyclerView.apply { // Make sure this ID matches your RecyclerView ID in the layout
//            layoutManager = LinearLayoutManager(context)
//            adapter = wishlistAdapter
//        }
//
//        viewModel.wishlistSubjects.observe(viewLifecycleOwner) { subjects ->
//            wishlistAdapter.submitList(subjects)
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}
