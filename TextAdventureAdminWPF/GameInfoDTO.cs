using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

namespace TextAdventureAdminWPF
{
    public class GameInfoDTO
    {
        [JsonPropertyName("author")]
        public string Author { get; set; }

        [JsonPropertyName("displayName")]
        public string DisplayName { get; set; }
    }
}
