package com.example.mobv_zadanie.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.example.mobv_zadanie.R
import com.example.mobv_zadanie.data.util.Injection
import com.example.mobv_zadanie.databinding.FragmentContactsBinding
import com.example.mobv_zadanie.ui.viewModels.ContactsViewModel

/**
 * A simple [Fragment] subclass.
 */
class ContactsFragment : Fragment() {

    private lateinit var contactsViewModel: ContactsViewModel
    private lateinit var binding: FragmentContactsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_contacts, container, false
        )

        // Specify the current activity as the lifecycle owner of the binding.
        // This is necessary so that the binding can observe LiveData updates.
        binding.lifecycleOwner = this

        // get ViewModel from dependency Injection. Injection class creates new ViewModel from ViewModelFactory
        contactsViewModel = ViewModelProvider(this, Injection.provideViewModelFactory(context!!))
            .get(ContactsViewModel::class.java)

        // set ViewModel
        binding.model = contactsViewModel

        // attach recycler view adapter
        val adapter = ContactsAdapter(ContactsListener { id ->
            contactsViewModel.onContactClicked(id)
        })
        binding.contactsList.adapter = adapter

        contactsViewModel.contacts.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

       contactsViewModel.navigateToContactRoom.observe(this, Observer { contact ->
            contact?.let {
                this.findNavController().navigate(ContactsFragmentDirections.actionContactsFragmentToChatFragment(contact))
                contactsViewModel.onContactNavigated() // reset state
            }
        })

        return binding.root
    }


}
