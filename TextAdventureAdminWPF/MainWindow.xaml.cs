using Microsoft.Win32;
using System.Collections.ObjectModel;
using System.IO;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Text.Json;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace TextAdventureAdminWPF
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        private ObservableCollection<GameInfoAvailableDTO> games = new ObservableCollection<GameInfoAvailableDTO>();
        private HttpClient httpClient = new HttpClient();
        public MainWindow()
        {
            InitializeComponent();
            // Set ListBox's ItemsSource to the ObservableCollection of games
            lstTextAdventures.ItemsSource = games;

            // Fetch available games when the window loads
            FetchAvailableGames();
        }
        private async void UploadButton_Click(object sender, RoutedEventArgs e)
        {
            // Open file dialog to select .json file
            OpenFileDialog openFileDialog = new OpenFileDialog();
            openFileDialog.Filter = "JSON files (*.json)|*.json";
            openFileDialog.InitialDirectory = @"C:\"; // Set initial directory as required
            openFileDialog.Title = "Select Text Adventure JSON File";

            if (openFileDialog.ShowDialog() == true)
            {
                // Process the selected file
                string filePath = openFileDialog.FileName;
                await UploadFileAsync(filePath);
                FetchAvailableGames();
                // Add your logic to handle the uploaded file
            }
        }
        private async void CheckBox_Checked(object sender, RoutedEventArgs e)
        {
            CheckBox checkBox = sender as CheckBox;
            if (checkBox != null && checkBox.Tag != null)
            {
                string gameId = checkBox.Tag.ToString();
                await ChangeGameStatus(gameId, true); // true indicates the game is now available
            }
        }

        private async void CheckBox_Unchecked(object sender, RoutedEventArgs e)
        {
            CheckBox checkBox = sender as CheckBox;
            if (checkBox != null && checkBox.Tag != null)
            {
                string gameId = checkBox.Tag.ToString();
                await ChangeGameStatus(gameId, false); // false indicates the game is now unavailable
            }
        }

        private async void FetchAvailableGames()
        {
            try
            {
                // Make an HTTP GET request to fetch available games
                HttpResponseMessage response = await httpClient.GetAsync("http://localhost:5000/availableGames");
                response.EnsureSuccessStatusCode(); // Throw an exception if the status code is not success

                // Read the response content as a string
                string responseBody = await response.Content.ReadAsStringAsync();

                // Deserialize the JSON string to a collection of GameInfoAvailableDTO objects
                GameInfoAvailableDTO[] gamesArray = JsonSerializer.Deserialize<GameInfoAvailableDTO[]>(responseBody);

                // Clear the existing collection and add the fetched games
                games.Clear();
                foreach (var game in gamesArray)
                {
                    games.Add(game);
                }
            }
            catch (HttpRequestException ex)
            {
                MessageBox.Show($"Error fetching available games: {ex.Message}", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
            }
            catch (JsonException ex)
            {
                MessageBox.Show($"Error deserializing response: {ex.Message}", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }
        private async Task UploadFileAsync(string filePath)
        {
            try
            {
                // Create a MultipartFormDataContent object
                MultipartFormDataContent formData = new MultipartFormDataContent();

                // Read the file and add it to the form data
                byte[] fileBytes = File.ReadAllBytes(filePath);
                ByteArrayContent byteArrayContent = new ByteArrayContent(fileBytes);
                byteArrayContent.Headers.ContentType = MediaTypeHeaderValue.Parse("application/json");
                formData.Add(byteArrayContent, "file", System.IO.Path.GetFileName(filePath));

                // Make an HTTP POST request to upload the file
                HttpResponseMessage response = await httpClient.PostAsync("http://localhost:5000/newAdventure", formData);
                response.EnsureSuccessStatusCode(); // Throw an exception if the status code is not success

                MessageBox.Show("File uploaded successfully!", "Success", MessageBoxButton.OK, MessageBoxImage.Information);

                // Refresh the available games list
                FetchAvailableGames();
            }
            catch (HttpRequestException ex)
            {
                MessageBox.Show($"Error uploading file: {ex.Message}", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }
        private async Task ChangeGameStatus(string gameId, bool status)
        {
            try
            {
                StatusDTO statusDTO = new StatusDTO
                {
                    Id = long.Parse(gameId),
                    Status = status
                };

                string json = JsonSerializer.Serialize(statusDTO);

                HttpRequestMessage request = new HttpRequestMessage(HttpMethod.Put, "http://localhost:5000/status");
                request.Content = new StringContent(json, Encoding.UTF8, "application/json");

                HttpResponseMessage response = await httpClient.SendAsync(request);

                if (response.IsSuccessStatusCode)
                {
                    MessageBox.Show($"Game with ID {gameId} status changed successfully.");
                }
                else
                {
                    MessageBox.Show($"Failed to change game status: {response.StatusCode}");
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Error changing game status: {ex.Message}");
            }
        }

    }
}

