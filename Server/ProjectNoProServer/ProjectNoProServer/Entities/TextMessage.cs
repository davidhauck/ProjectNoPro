using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ProjectNoProServer.Entities
{
    public class TextMessage
    {
        public int SenderId { get; set; }
        public string SenderName { get; set; }
        public string Text { get; set; }
    }
}