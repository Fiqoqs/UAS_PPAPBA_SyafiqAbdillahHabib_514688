package com.example.uas_ppapba_syafiqabdillahhabib_514688.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.uas_ppapba_syafiqabdillahhabib_514688.AuthManager
import com.example.uas_ppapba_syafiqabdillahhabib_514688.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    private lateinit var authManager: AuthManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        authManager = AuthManager(requireContext())

        val logoutButton: Button = view.findViewById(R.id.logout_button)
        val userRoleText: TextView = view.findViewById(R.id.user_role_text)

        userRoleText.text = if (authManager.isAdmin) "Admin" else "User"

        logoutButton.setOnClickListener {
            authManager.logout()
            requireActivity().finish()
        }

        return view
    }
}
