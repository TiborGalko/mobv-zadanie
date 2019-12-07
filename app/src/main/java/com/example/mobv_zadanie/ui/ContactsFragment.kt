package com.example.mobv_zadanie.ui


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.example.mobv_zadanie.R
import com.example.mobv_zadanie.data.util.Injection
import com.example.mobv_zadanie.data.util.SharedPrefWorker
import com.example.mobv_zadanie.databinding.FragmentContactsBinding
import com.example.mobv_zadanie.ui.viewModels.ContactsViewModel

/**
 * A simple [Fragment] subclass.
 */
class ContactsFragment : Fragment() {

    //helper global variable
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var contactsContext: Context
    }

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

        binding.toWifiRoomsBtn.setOnClickListener {
            this.findNavController().navigate(
                ContactsFragmentDirections.actionContactsFragmentToWifiRoomsFragment()
            )
        }

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
                this.findNavController().navigate(ContactsFragmentDirections.actionContactsFragmentToChatmessagesFragment(contact))
                contactsViewModel.onContactNavigated() // reset state
            }
        })

        // Load contacts from api and save to database
        contactsViewModel.listContacts(binding.root.context)

        return binding.root
    }

    //after fragment onCreateView method
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //pass context to use for SharedPrefWorker
        contactsContext = view.context
    }

    //enable options menu in this fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    //inflate menu - need menu because user should have ability to uncheck auto login
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.custom_menu, menu)
        //set value of user settings
        menu.getItem(0).isChecked = SharedPrefWorker.getBoolean(contactsContext, "autoLoginChecked")!!
        super.onCreateOptionsMenu(menu, inflater)
    }

    //select actions for menu
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_autoLogin -> {

            item.isChecked = !item.isChecked
            SharedPrefWorker.saveBoolean(contactsContext, "autoLoginChecked", item.isChecked)
            true
        }
        R.id.action_logout -> {
            findNavController().navigate(R.id.loginFragment)
            true
        }
        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }
}
