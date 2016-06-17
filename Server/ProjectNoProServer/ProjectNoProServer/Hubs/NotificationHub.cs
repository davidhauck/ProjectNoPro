using Microsoft.AspNet.SignalR;
using Microsoft.AspNet.SignalR.Hubs;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Claims;
using System.Threading.Tasks;
using System.Web;

namespace ProjectNoProServer.Hubs
{
    [HubName("notification")]
    public class NotificationHub : Hub
    {
        public Task JoinGroup(string s)
        {
            string id = null;
            foreach (var claim in (Context.User.Identity as ClaimsIdentity).Claims)
            {
                if (claim.Type == "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/nameidentifier")
                    id = claim.Value;
            }
            if (id == null)
                throw new Exception("Not logged in?");
            return Groups.Add(Context.ConnectionId, id);
        }
    }
}