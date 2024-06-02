using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

namespace TextAdventureAdminWPF
{
    public class GameInfoAvailableDTO
    {

        [JsonPropertyName("gameInfoDTO")]
        public GameInfoDTO GameInfoDTO { get; set; }

        [JsonPropertyName("adventureGameModel")]
        public AdventureGameModel AdventureGameModel { get; set; }
    }
}
