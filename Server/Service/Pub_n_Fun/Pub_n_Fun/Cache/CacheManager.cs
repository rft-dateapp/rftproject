using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Pub_n_Fun.Cache
{
    public static class CacheManager
    {
        private static List<string> ValidatedTokens = new List<string>() { "mastertoken" };

        internal static bool ValidateToken(string token)
        {
            if (ValidatedTokens.Contains(token.ToLower()))
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        internal static void RemoveRoken(string token)
        {
            ValidatedTokens.Remove(token);
        }

        internal static void AddToken(string token)
        {
            ValidatedTokens.Add(token);
        }
    }
}