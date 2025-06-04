class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val connectButton = findViewById<Button>(R.id.connectButton)
        var isConnected = false

        connectButton.setOnClickListener {
            if (!isConnected) {
                // Fake "Connecting..." animation
                connectButton.text = "CONNECTING..."
                Handler(Looper.getMainLooper()).postDelayed({
                    connectButton.text = "DISCONNECT"
                    connectButton.setBackgroundColor(Color.RED)
                    changeDNS() // Call DNS function
                    Toast.makeText(this, "DNS Changed (Not a real VPN!)", Toast.LENGTH_SHORT).show()
                }, 1500)
            } else {
                connectButton.text = "CONNECT"
                connectButton.setBackgroundColor(Color.GREEN)
                Toast.makeText(this, "Disconnected", Toast.LENGTH_SHORT).show()
            }
            isConnected = !isConnected
        }
    }

    // DNS Changer Function (Requires Root!)
    private fun changeDNS() {
        try {
            val dns1 = "185.51.200.2"
            val dns2 = "178.22.122.100"
            val process = Runtime.getRuntime().exec("su")
            val output = DataOutputStream(process.outputStream)
            output.writeBytes("ndc resolver setnetdns eth0 $dns1 $dns2\n")
            output.writeBytes("exit\n")
            output.flush()
            process.waitFor()
        } catch (e: Exception) {
            Log.e("DNS_ERROR", "Failed (Root?): ${e.message}")
        }
    }
}